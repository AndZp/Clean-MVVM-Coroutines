package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.utils.logWarning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserFavoritesMovies(
    private val repo: MoviesRepo,
    private val userManager: UserManager
) : BaseUseCase<List<MovieFullDetails>, NoParams>() {

    override suspend fun run(params: NoParams): Either<GetUserFavoriteFailure, List<MovieFullDetails>> {
        return when (val result = userManager.getOrCreateUser()) {
            is Result.Success -> onUserReceived(result)
            is Result.Error -> onErrorReceiveUser(result)
        }
    }

    private fun onErrorReceiveUser(result: Result.Error): Either.Left<GetUserFavoriteFailure.LoadError> {
        logWarning("onErrorReceiveUser", result.exception)
        return Either.Left(GetUserFavoriteFailure.LoadError(result.exception))
    }

    private suspend fun onUserReceived(result: Result.Success<User>): Either.Right<List<MovieFullDetails>> {
        val userFavoritesMovies = result.data.favoritesMovies
            .map { id -> withContext(Dispatchers.IO) { repo.getMovie(id) } }
            .filterIsInstance<Result.Success<MovieFullDetails>>()
            .map { movieResult: Result.Success<MovieFullDetails> -> movieResult.data }
        return Either.Right(userFavoritesMovies)
    }

}

object NoParams : Any()

sealed class GetUserFavoriteFailure(featureException: Exception = Exception("Feature failure")) :
    Failure.FeatureFailure(featureException) {
    data class LoadError(val error: Exception) : GetUserFavoriteFailure(error)
}
