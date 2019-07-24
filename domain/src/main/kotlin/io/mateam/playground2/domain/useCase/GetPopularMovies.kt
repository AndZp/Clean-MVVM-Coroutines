package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either

class GetPopularMovies(private val repo: MoviesRepo) : BaseUseCase<PopularMovies, GetPopularMovies.None>() {

    override suspend fun run(params: None): Either<GetPopularFailure, PopularMovies> {
        return when (val result = repo.getPopularMovies()) {
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(GetPopularFailure(result.exception))
        }
    }

    class None : Any()

    data class GetPopularFailure(val error: Exception) : Failure.FeatureFailure(error)
}