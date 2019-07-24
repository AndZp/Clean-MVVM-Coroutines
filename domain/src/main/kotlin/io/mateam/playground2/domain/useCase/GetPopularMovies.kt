package io.mateam.playground2.domain.useCase

import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.utils.Either
import io.mateam.playground2.domain.entity.Result

class GetPopularMovies(private val repo: MoviesRepo) : BaseUseCase<PopularMovies, Nothing>() {
    override suspend fun run(params: Nothing): Either<Failure, PopularMovies> {
        return when (val result = repo.getPopularMovies()){
            is Result.Success -> Either.Right(result.data)
            is Result.Error -> Either.Left(Failure.FeatureFailure(result.exception))
        }
    }
}