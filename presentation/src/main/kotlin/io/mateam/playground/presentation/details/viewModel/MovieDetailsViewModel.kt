package io.mateam.playground.presentation.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mateam.playground.presentation.details.entity.ReviewUiModel
import io.mateam.playground.presentation.details.mapper.MoviesDetailsUiMapper
import io.mateam.playground.presentation.popular.paginator.PaginationHelper
import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.MovieReviews
import io.mateam.playground2.domain.entity.Review
import io.mateam.playground2.domain.useCase.GetMovieReview
import io.mateam.playground2.domain.useCase.GetMoviesByID
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class MovieDetailsViewModel(
    private val movieId: Int,
    private val getReviews: GetMovieReview,
    private val uiMapper: MoviesDetailsUiMapper,
    private val reviewsPaginationHelper: PaginationHelper<Review>
) : ViewModel() {

    val reviewState = MutableLiveData<MoviesReviewState>()

    fun loadMoreReview() {
        logDebug("loadMoreReview: paginationHelper.nextPage [${reviewsPaginationHelper.nextPage}]")
        reviewState.postValue(MoviesReviewState.Loading)
        val params = GetMovieReview.Param(movieId, reviewsPaginationHelper.nextPage)
        getReviews(viewModelScope, params) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleFailure(failure: Failure) {
        logDebug("handleFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is GetMoviesByID.GetMovieFailure.LoadError -> reviewState.postValue(MoviesReviewState.LoadingError)
            else -> logWarning("handleFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleSuccess(movieReviews: MovieReviews) {
        logDebug("handleSuccess, movie revies =  id = [${movieReviews.id}, page [${movieReviews.page}, reviews size = [${movieReviews.reviews.size}")
        reviewsPaginationHelper.onSuccessLoad(movieReviews.reviews)
        postPopularMovies(reviewsPaginationHelper.allItems)
    }

    private fun postPopularMovies(reviews: List<Review>) {
        val uiMoviesModels = uiMapper.mapReview(reviews)
        reviewState.postValue(MoviesReviewState.Success(uiMoviesModels))
    }
}


sealed class MoviesReviewState {
    object Loading : MoviesReviewState()
    object LoadingError : MoviesReviewState()
    data class Success(val reviews: List<ReviewUiModel>) : MoviesReviewState()
}