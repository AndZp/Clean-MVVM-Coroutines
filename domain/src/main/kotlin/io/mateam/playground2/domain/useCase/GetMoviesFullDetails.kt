package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class GetMoviesFullDetails(private val repo: MoviesRepo) : BaseUseCase<MovieFullDetails, GetMoviesFullDetails.Param>() {
    override suspend fun run(params: Param): Either<GetMovieFailure, MovieFullDetails> {
        return when (val result = repo.getMovie(params.id)) {
            is Result.Success -> onSuccessResult(result)
            is Result.Error -> onErrorResult(result)
        }
    }

    private fun onErrorResult(result: Result.Error): Either.Left<GetMovieFailure.LoadError> {
        logWarning("onErrorResult", result.exception)
        return Either.Left(GetMovieFailure.LoadError(result.exception))
    }

    private fun onSuccessResult(result: Result.Success<MovieFullDetails>): Either.Right<MovieFullDetails> {
        val movie = result.data
        logDebug("onSuccess: result movie title [${movie.title}], id [${movie.title}]")
        return Either.Right(movie)
    }

    data class Param(val id: Int)

    sealed class GetMovieFailure(featureException: Exception = Exception("Feature failure")) :
        Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception) : GetMovieFailure(error)
    }
}