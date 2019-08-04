package io.mateam.playground.presentation.common.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.common.adapter.MoviesAdapter.ViewType.LOADING
import io.mateam.playground.presentation.common.adapter.MoviesAdapter.ViewType.MOVIE
import io.mateam.playground.presentation.common.adapter.MoviesAdapter.ViewType.TOP_MOVIE
import io.mateam.playground.presentation.popular.diffUtils.MovieListItemsDiffCallback
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.utils.GlideApp
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.item_hero.view.movie_desc
import kotlinx.android.synthetic.main.item_hero.view.movie_poster
import kotlinx.android.synthetic.main.item_hero.view.movie_title
import kotlinx.android.synthetic.main.item_hero.view.movie_year
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class MoviesAdapter(private val context: Context, private val onMovieClick: ((MovieUiModel, ImageView) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesItems: MutableList<MoviesListItem> = mutableListOf()

    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            MOVIE -> {
                val viewItem = inflater.inflate(R.layout.item_list, parent, false)
                viewHolder = MovieVH(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }
            TOP_MOVIE -> {
                val viewHero = inflater.inflate(R.layout.item_hero, parent, false)
                viewHolder = TopMovie(viewHero)
            }
            else -> throw IllegalArgumentException("Unknown viewType [$viewType]")
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = moviesItems[position]

        when (getItemViewType(position)) {
            TOP_MOVIE -> {
                val topMovieHolder = holder as TopMovie
                val movie = (item as MoviesListItem.Top).movie
                topMovieHolder.bind(movie, onMovieClick)
            }
            MOVIE -> {
                val movieVH = holder as MovieVH
                val movie = (item as MoviesListItem.Movie).movie
                movieVH.bind(movie, onMovieClick)
            }
            LOADING -> {
                val loadingVH = holder as LoadingVH
                loadingVH.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return moviesItems[position].viewType
    }

    private fun formatYearLabel(movie: MovieUiModel): String {
        return ("${movie.releaseData?.substring(0, 4)} | ${movie.originalLanguage?.toUpperCase()}")
    }

    private fun loadImage(imageUrl: String): RequestBuilder<Drawable> {
        return GlideApp
            .with(context)
            .load(imageUrl)
            .centerCrop()
    }

    fun update(updatedMovies: List<MovieUiModel>) {
        val movieItems = updatedMovies.map { movieUiModel ->
            MoviesListItem.Movie(
                movieUiModel
            )
        }
        logDebug("update: old size [${this.moviesItems.size}], updated size [${movieItems.size}]")
        val diffResult = DiffUtil.calculateDiff(
            MovieListItemsDiffCallback(
                movieItems,
                this.moviesItems
            )
        )
        moviesItems.apply {
            clear()
            addAll(movieItems)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            moviesItems.add(MoviesListItem.Loading)
            notifyItemInserted(moviesItems.lastIndex)
        }
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        if (moviesItems.isEmpty()) return
        val position = moviesItems.lastIndex
        moviesItems.removeAt(position)
        notifyItemRemoved(position)
    }

    @Suppress("unused")
    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(moviesItems[0])
        }
    }

    private fun remove(r: MoviesListItem) {
        val position = moviesItems.indexOf(r)
        if (position > -1) {
            moviesItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * Header ViewHolder
     */
    private inner class TopMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            movie: MovieUiModel,
            onMovieClick: (MovieUiModel, ImageView) -> Unit
        ) {
            itemView.setOnClickListener { onMovieClick.invoke(movie, itemView.movie_poster) }

            itemView.movie_title.text = movie.title
            itemView.movie_year.text = formatYearLabel(movie)
            itemView.movie_desc.text = movie.overview
            movie.imageUrl?.let { loadImage(it).into(itemView.movie_poster) }
        }
    }

    private inner class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            movie: MovieUiModel,
            onMovieClick: (MovieUiModel, ImageView) -> Unit
        ) {

            itemView.setOnClickListener { onMovieClick.invoke(movie, itemView.movie_poster) }

            itemView.movie_title.text = movie.title
            itemView.movie_year.text = formatYearLabel(movie)
            itemView.movie_desc.text = movie.overview

            // load movie thumbnail
            movie.imageUrl?.let {
                loadImage(it)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemView.movie_progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            // image ready, hide progress now
                            itemView.movie_progress.visibility = View.GONE
                            return false   // return false if you want Glide to handle everything else.
                        }
                    })
                    .into(itemView.movie_poster)
            }
        }
    }


    private inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.loadmore_errorlayout.visibility = View.GONE
            itemView.loadmore_progress.visibility = View.VISIBLE
        }
    }

    companion object ViewType {
        const val MOVIE = 0
        const val LOADING = 1
        const val TOP_MOVIE = 2
    }
}

sealed class MoviesListItem(val viewType: Int) {
    data class Top(val movie: MovieUiModel) : MoviesListItem(TOP_MOVIE)
    data class Movie(val movie: MovieUiModel) : MoviesListItem(MOVIE)
    object Loading : MoviesListItem(LOADING)
}