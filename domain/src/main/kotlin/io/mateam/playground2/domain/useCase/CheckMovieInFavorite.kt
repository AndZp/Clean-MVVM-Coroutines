package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class CheckMovieInFavorite(private val userManager: UserManager) : BaseUseCase<Boolean, CheckMovieInFavorite.Param>() {
    override suspend fun run(params: Param): Either<CheckFavoriteStateFailure, Boolean> {
        return when (val result = userManager.getOrCreateUser()) {
            is Result.Success -> onSuccessResult(result, params.id)
            is Result.Error -> onErrorResult(result)
        }
    }

    private fun onErrorResult(result: Result.Error): Either.Left<CheckFavoriteStateFailure.LoadError> {
        logWarning("onErrorResult", result.exception)
        return Either.Left(CheckFavoriteStateFailure.LoadError(result.exception))
    }

    private fun onSuccessResult(result: Result.Success<User>, movieId: Int): Either.Right<Boolean> {
        val user = result.data
        val isInFavorite = user.favoritesMovies.isFavorite(movieId)
        logDebug("onSuccess: result movieId [$movieId], for user [${user.id}] -  isInFavorite = [$isInFavorite] ")
        return Either.Right(isInFavorite)
    }

    data class Param(val id: Int)

    sealed class CheckFavoriteStateFailure(featureException: Exception = Exception("Feature failure")) :
        Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception) : CheckFavoriteStateFailure(error)
    }
}