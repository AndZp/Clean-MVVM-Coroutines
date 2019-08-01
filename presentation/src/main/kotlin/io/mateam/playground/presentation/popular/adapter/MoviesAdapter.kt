package io.mateam.playground.presentation.popular.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.popular.viewModel.entity.MovieUiModel
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.item_hero.view.movie_desc
import kotlinx.android.synthetic.main.item_hero.view.movie_poster
import kotlinx.android.synthetic.main.item_hero.view.movie_title
import kotlinx.android.synthetic.main.item_hero.view.movie_year
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class MoviesAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviesItems: MutableList<MoviesListItem> = mutableListOf()

    private var isLoadingAdded = false
    private var retryPageLoad = false

   // private val mCallback: PaginationAdapterCallback = context as PaginationAdapterCallback

    private var errorMsg: String? = null
    val isEmpty: Boolean get() = itemCount == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
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
                topMovieHolder.bind(movie)
            }

            MOVIE -> {
                val movieVH = holder as MovieVH
                val movie = (item as MoviesListItem.Movie).movie
                movieVH.bind(movie)
            }

            LOADING -> {
                val loadingVH = holder as LoadingVH
                loadingVH.bind(retryPageLoad)
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesItems.size
    }

    override fun getItemViewType(position: Int): Int {
      return if (position == moviesItems.size - 1 && isLoadingAdded) LOADING else MOVIE

        /*return if (position == 0) {
            TOP_MOVIE
        } else {
            if (position == moviesItems.size - 1 && isLoadingAdded) LOADING else MOVIE
        }*/
    }

    private fun formatYearLabel(movie: MovieUiModel): String {
        return (movie.releaseData.substring(0, 4)
                + " | "
                + movie.originalLanguage.toUpperCase())
    }


    private fun loadImage(imageUrl: String): RequestBuilder<Drawable> {
        return Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
    }

    fun update(updatedMovies: List<MovieUiModel>) {
        val movieItems = updatedMovies.map { movieUiModel -> MoviesListItem.Movie(movieUiModel) }
        logDebug("update: old size [${this.moviesItems.size}], updated size [${movieItems.size}]")
        val diffResult = DiffUtil.calculateDiff(MovieListItemsDiffCallback(this.moviesItems, movieItems))
        moviesItems.apply {
            clear()
            addAll(movieItems)
        }
        diffResult.dispatchUpdatesTo(this)
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        moviesItems.add(MoviesListItem.Loading)
        notifyItemInserted(moviesItems.lastIndex)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = moviesItems.lastIndex
        val result = moviesItems[position]
        moviesItems.removeAt(position)
        notifyItemRemoved(position)
    }

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
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    fun showRetry(show: Boolean, errorMsg: String?) {
        retryPageLoad = show
        notifyItemChanged(moviesItems.size - 1)

        if (errorMsg != null) this.errorMsg = errorMsg
    }


    /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Header ViewHolder
     */
    protected inner class TopMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieUiModel) {
            itemView.movie_title.text = movie.title
            itemView.movie_year.text = formatYearLabel(movie)
            itemView.movie_desc.text = movie.overview
            loadImage(movie.imageUrl).into(itemView.movie_poster)
        }
    }

    /**
     * Main list's content ViewHolder
     */
    protected inner class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieUiModel) {
            itemView.movie_title.text = movie.title
            itemView.movie_year.text = formatYearLabel(movie)
            itemView.movie_desc.text = movie.overview

            // load movie thumbnail
            loadImage(movie.imageUrl)
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


    protected inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.loadmore_retry.setOnClickListener(this)
            itemView.loadmore_errorlayout.setOnClickListener(this)
        }

        fun bind(retryPageLoad: Boolean) {
            if (retryPageLoad) {
                itemView.loadmore_errorlayout.visibility = View.VISIBLE
                itemView.loadmore_progress.visibility = View.GONE

                itemView.loadmore_errortxt.text = if (errorMsg != null)
                    errorMsg
                else
                    context.getString(R.string.error_msg_unknown)

            } else {
                itemView.loadmore_errorlayout.visibility = View.GONE
                itemView.loadmore_progress.visibility = View.VISIBLE
            }
        }

        override fun onClick(view: View) {
            when (view.id) {

            }/*case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;*/
        }


    }

    companion object {
        // View Types
        private const val MOVIE = 0
        private const val LOADING = 1
        private const val TOP_MOVIE = 2
    }
}

sealed class MoviesListItem {
    data class Top(val movie: MovieUiModel):MoviesListItem()
    data class Movie(val movie: MovieUiModel):MoviesListItem()
    object Loading:MoviesListItem()
}