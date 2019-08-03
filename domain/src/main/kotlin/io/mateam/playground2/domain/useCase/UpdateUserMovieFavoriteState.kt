package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class UpdateUserMovieFavoriteState(private val userManager: UserManager) : BaseUseCase<Unit, UpdateUserMovieFavoriteState.Param>() {
    override suspend fun run(params: Param): Either<CheckFavoriteStateFailure, Unit> {
        return when (val result = userManager.getOrCreateUser()) {
            is Result.Success -> onSuccessResult(result, params)
            is Result.Error -> onErrorResult(result)
        }
    }

    private fun onErrorResult(result: Result.Error): Either.Left<CheckFavoriteStateFailure.LoadError> {
        logWarning("onErrorResult", result.exception)
        return Either.Left(CheckFavoriteStateFailure.LoadError(result.exception))
    }

    private suspend fun onSuccessResult(result: Result.Success<User>, param: Param): Either.Right<Unit> {
        val user = result.data
        if (param.inFavorite){
            user.favoritesMovies.add(param.movieId)
        } else {
            user.favoritesMovies.remove(param.movieId)
        }
        userManager.saveUser(user)
        logDebug("onSuccess: for movie id = [${param.movieId}, state updated to inFavorite =[${param.inFavorite}]] ")
        return Either.Right(Unit)
    }

    data class Param(val movieId: Int, val inFavorite: Boolean)

    sealed class CheckFavoriteStateFailure(featureException: Exception = Exception("Feature failure")) :
        Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception) : CheckFavoriteStateFailure(error)
    }
}