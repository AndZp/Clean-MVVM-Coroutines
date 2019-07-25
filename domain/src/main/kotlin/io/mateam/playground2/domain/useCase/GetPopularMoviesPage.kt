package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class GetPopularMoviesPage(private val repo: MoviesRepo) : BaseUseCase<PopularMovies, GetPopularMoviesPage.Param>() {

    private var isEndReached = false

    override suspend fun run(params: Param): Either<GetPopularFailure, PopularMovies> {
        return if (isEndReached) {
            logDebug("End already reached. return failure result")
            Either.Left(GetPopularFailure.EndReached)
        } else
            when (val result = repo.getPopularMovies(params.page)) {
                is Result.Success -> onSuccessResult(result)
                is Result.Error -> onErrorResult(result)
            }
    }

    private fun onErrorResult(result: Result.Error): Either.Left<GetPopularFailure.LoadError> {
        logWarning("onErrorResult", result.exception)
        return Either.Left(GetPopularFailure.LoadError(result.exception))
    }

    private fun onSuccessResult(result: Result.Success<PopularMovies>): Either.Right<PopularMovies> {
        logDebug("onSuccess: result [${result.data}]")
        checkIsEndWasReached(result.data)
        return Either.Right(result.data)
    }

    private fun checkIsEndWasReached(popularMovies: PopularMovies) {
        isEndReached = popularMovies.page >= popularMovies.totalPages
        logDebug("checkIsEndWasReached: isEndReached [$isEndReached]. Last loaded page [${popularMovies.page}, total pages [${popularMovies.totalPages}]]")
    }

    data class Param(val page : Int)

    sealed class GetPopularFailure(featureException: Exception = Exception("Feature failure")) : Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception):GetPopularFailure(error)
        object EndReached : GetPopularFailure()
    }
}