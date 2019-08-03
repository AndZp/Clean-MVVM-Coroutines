package io.mateam.playground.presentation.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mateam.playground.presentation.details.entity.ReviewUiModel
import io.mateam.playground.presentation.details.mapper.MoviesDetailsUiMapper
import io.mateam.playground.presentation.popular.paginator.PaginationHelper
import io.mateam.playground2.domain.entity.movie.Genre
import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.movie.MovieReviews
import io.mateam.playground2.domain.entity.movie.Review
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.useCase.GetMovieReview
import io.mateam.playground2.domain.useCase.GetMoviesFullDetails
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class MovieDetailsViewModel(
    private val movieId: Int,
    private val getReviews: GetMovieReview,
    private val getMoviesFullDetails: GetMoviesFullDetails,
    private val uiMapper: MoviesDetailsUiMapper,
    private val reviewsPaginationHelper: PaginationHelper<Review>
) : ViewModel() {

    val reviewState = MutableLiveData<MoviesReviewState>()
    val genersTags = MutableLiveData<List<String>>()

    init {
        loadFullDetails()
        loadMoreReview()
    }

    private fun loadFullDetails() {
        logDebug("loadFullDetails: movieId [$movieId]")
        val params = GetMoviesFullDetails.Param(movieId)
        getMoviesFullDetails(viewModelScope, params) { it.either(::handleDetailsFailure, ::handleDetailsSuccess) }
    }

    fun loadMoreReview() {
        logDebug("loadMoreReview: paginationHelper.nextPage [${reviewsPaginationHelper.nextPage}]")
        reviewState.postValue(MoviesReviewState.Loading)
        val params = GetMovieReview.Param(movieId, reviewsPaginationHelper.nextPage)
        getReviews(viewModelScope, params) { it.either(::handleReviewFailure, ::handleReviewSuccess) }
    }

    private fun handleDetailsFailure(failure: Failure) {
        logDebug("handleDetailsFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is GetMoviesFullDetails.GetMovieFailure.LoadError -> reviewState.postValue(MoviesReviewState.LoadingError)
            else -> logWarning("handleReviewFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleDetailsSuccess(movieFullDetails: MovieFullDetails) {
        logDebug("handleDetailsSuccess, movie  id = [${movieFullDetails.id}, title [${movieFullDetails.title}")
        postGenres(movieFullDetails.genres)
    }


    private fun handleReviewFailure(failure: Failure) {
        logDebug("handleReviewFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is GetMoviesFullDetails.GetMovieFailure.LoadError -> reviewState.postValue(MoviesReviewState.LoadingError)
            else -> logWarning("handleReviewFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleReviewSuccess(movieReviews: MovieReviews) {
        logDebug("handleReviewSuccess, movie revies =  id = [${movieReviews.id}, page [${movieReviews.page}, reviews size = [${movieReviews.reviews.size}")
        reviewsPaginationHelper.onSuccessLoad(movieReviews.reviews)
        postPopularMovies(reviewsPaginationHelper.allItems)
    }

    private fun postPopularMovies(reviews: List<Review>) {
        val uiMoviesModels = uiMapper.mapReview(reviews)
        reviewState.postValue(MoviesReviewState.Success(uiMoviesModels))
    }

    private fun postGenres(genres: List<Genre>) {
        val genresTags = genres.map { it.name }
        genersTags.postValue(genresTags)
    }
}


sealed class MoviesReviewState {
    object Loading : MoviesReviewState()
    object LoadingError : MoviesReviewState()
    data class Success(val reviews: List<ReviewUiModel>) : MoviesReviewState()
}