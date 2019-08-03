package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class UpdateUserMovieFavoriteState(private val userManager: UserManager) : BaseUseCase<Unit, UpdateUserMovieFavoriteState.Param>() {
    override suspend fun run(params: Param): Either<UpdateFavoriteStateFailure, Unit> {
        return when (val result = userManager.getOrCreateUser()) {
            is Result.Success -> onUserReceived(result, params)
            is Result.Error -> onErrorReceiveUser(result)
        }
    }

    private fun onErrorReceiveUser(result: Result.Error): Either.Left<UpdateFavoriteStateFailure.LoadError> {
        logWarning("onErrorReceiveUser", result.exception)
        return Either.Left(UpdateFavoriteStateFailure.LoadError(result.exception))
    }

    private suspend fun onUserReceived(result: Result.Success<User>, param: Param): Either.Right<Unit> {
        val user = result.data
        if (param.inFavorite){
            user.favoritesMovies.add(param.movieId)
        } else {
            user.favoritesMovies.remove(param.movieId)
        }
        userManager.updateUser(user)
        logDebug("onSuccess: for movie id = [${param.movieId}, state updated to inFavorite =[${param.inFavorite}]] ")
        return Either.Right(Unit)
    }

    data class Param(val movieId: Int, val inFavorite: Boolean)

    sealed class UpdateFavoriteStateFailure(featureException: Exception = Exception("Feature failure")) : Failure.FeatureFailure(featureException) {
        data class LoadError(val error: Exception) : UpdateFavoriteStateFailure(error)
    }
}