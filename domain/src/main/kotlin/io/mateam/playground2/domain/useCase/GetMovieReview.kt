package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.MovieReviews
import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class GetMovieReview(private val repo: MoviesRepo) : BaseUseCase<MovieReviews, GetMovieReview.Param>() {
    override suspend fun run(params: Param): Either<GetReviewsFailure, MovieReviews> {
        return when (val result = repo.getReviews(params.movieId, params.page)) {
            is Result.Success -> onSuccessResult(result)
            is Result.Error -> onErrorResult(result)
        }
    }

    private fun onErrorResult(result: Result.Error): Either.Left<GetReviewsFailure.LoadError> {
        logWarning("onErrorResult", result.exception)
        return Either.Left(GetReviewsFailure.LoadError(result.exception))
    }

    private fun onSuccessResult(result: Result.Success<MovieReviews>): Either.Right<MovieReviews> {
        val movie = result.data
        logDebug("onSuccess: result movie id [${movie.id}], page [${movie.page}], reviews size [${movie.reviews.size}]")
        return Either.Right(movie)
    }

    data class Param(val movieId: Int, val page: Int)

    sealed class GetReviewsFailure(featureException: Exception = Exception("Feature failure")) :
        Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception) : GetReviewsFailure(error)
    }
}