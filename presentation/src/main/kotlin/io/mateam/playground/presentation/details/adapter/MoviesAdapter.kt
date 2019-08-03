package io.mateam.playground.presentation.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.details.entity.ReviewUiModel
import io.mateam.playground.presentation.common.adapter.MoviesAdapter.ViewType.LOADING
import io.mateam.playground.presentation.common.adapter.MoviesAdapter.ViewType.MOVIE
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItems: MutableList<ReviewListItem> = mutableListOf()

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            REVIEW -> {
                val viewItem = inflater.inflate(R.layout.item_review, parent, false)
                viewHolder = ReviewVH(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }
            else -> throw IllegalArgumentException("Unknown viewType [$viewType]")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listItems[position]

        when (getItemViewType(position)) {
            REVIEW -> {
                val reviewVH = holder as ReviewVH
                val movie = (item as ReviewListItem.Review).review
                reviewVH.bind(movie)
            }
            LOADING -> {
                val loadingVH = holder as LoadingVH
                loadingVH.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return listItems[position].viewType
    }

    fun update(updatedReviews: List<ReviewUiModel>) {
        val updatedItems = updatedReviews.map { reviewUiModel -> ReviewListItem.Review(reviewUiModel) }
        logDebug("update: old size [${this.listItems.size}], updated size [${updatedItems.size}]")
        val diffResult = DiffUtil.calculateDiff(ReviewItemsDiffCallback(updatedItems, this.listItems))
        listItems.apply {
            clear()
            addAll(updatedItems)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            listItems.add(ReviewListItem.Loading)
            notifyItemInserted(listItems.lastIndex)
        }
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        if (listItems.isEmpty()) return
        val position = listItems.lastIndex
        listItems.removeAt(position)
        notifyItemRemoved(position)
    }

    @Suppress("unused")
    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(listItems[0])
        }
    }

    private fun remove(r: ReviewListItem) {
        val position = listItems.indexOf(r)
        if (position > -1) {
            listItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private inner class ReviewVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            review: ReviewUiModel
        ) {
            itemView.item_review_title.text = review.author
            itemView.item_review_content.text = review.content
        }
    }

    private inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.loadmore_errorlayout.visibility = View.GONE
            itemView.loadmore_progress.visibility = View.VISIBLE
        }
    }

    companion object ViewType {
        const val REVIEW = 0
        const val LOADING = 1
    }
}

sealed class ReviewListItem(val viewType: Int) {
    data class Review(val review: ReviewUiModel) : ReviewListItem(MOVIE)
    object Loading : ReviewListItem(LOADING)
}