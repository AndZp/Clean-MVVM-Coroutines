package io.mateam.playground.presentation.details.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.details.adapter.ReviewsAdapter
import io.mateam.playground.presentation.details.entity.ReviewUiModel
import io.mateam.playground.presentation.details.viewModel.MovieDetailsViewModel
import io.mateam.playground.presentation.details.viewModel.MoviesReviewState
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.utils.EndlessRecyclerViewScrollListener
import io.mateam.playground.presentation.utils.GlideApp
import io.mateam.playground.presentation.utils.NotNullParcelableArg
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.fragment_movies_details.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesDetailsFragment : Fragment() {
    private val movie: MovieUiModel by NotNullParcelableArg(MOVIE_MODEL_ARG_KEY)

    private val moviesDetailsViewModel: MovieDetailsViewModel by viewModel { parametersOf(movie.id) }


    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var paginationListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovieModel()
        initReviewsRV()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        moviesDetailsViewModel.loadMoreReview()
    }

    private fun bindMovieModel() {
        detail_header_title.text = movie.title
        detail_header_release.text = movie.releaseData
        detail_header_star.rating = (movie.voteAverage / 2).toFloat()
        detail_body_summary.text = movie.overview
        GlideApp.with(requireContext())
            .load(movie.imageUrl)
            .into(movie_detail_poster)
    }


    private fun initReviewsRV() {
        reviewsAdapter = ReviewsAdapter(requireContext())

        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        paginationListener = buildPaginationScrollListener(linearLayoutManager)
        with(detail_body_recyclerView_reviews) {
            adapter = reviewsAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(paginationListener)
        }
    }

    private fun buildPaginationScrollListener(layoutManager: LinearLayoutManager): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                logDebug("onLoadMore")
                moviesDetailsViewModel.loadMoreReview()
            }
        }
    }


    private fun initViewModel() {
        moviesDetailsViewModel.reviewState.observe(this, Observer { onReviewStateChanged(it) })
    }

    private fun onReviewStateChanged(state: MoviesReviewState?) {
        logDebug("onReviewStateChanged: state [${state?.javaClass?.simpleName}]")
        when (state) {
            is MoviesReviewState.Loading -> showReviewLoading()
            is MoviesReviewState.LoadingError -> showReviewLoadingError()
            is MoviesReviewState.Success -> updateReviewList(state.reviews)
        }
    }

    private fun showReviewLoading() {
        logDebug("showReviewLoading")
        reviewsAdapter.addLoadingFooter()
    }

    private fun showReviewLoadingError() {
        logDebug("showReviewLoadingError")
        Toast.makeText(requireContext(), "error to load reviews", Toast.LENGTH_LONG).show()
        reviewsAdapter.removeLoadingFooter()
    }

    private fun updateReviewList(reviews: List<ReviewUiModel>) {
        logDebug("updateReviewList reviews [${reviews.size}]")
        reviewsAdapter.update(reviews)
    }

    companion object {
        private const val MOVIE_MODEL_ARG_KEY = "io.mateam.movie.MOVIE_MODEL"

        fun buildBundle(movie: MovieUiModel) =
            Bundle().apply {
                putParcelable(MOVIE_MODEL_ARG_KEY, movie)
            }
    }
}

