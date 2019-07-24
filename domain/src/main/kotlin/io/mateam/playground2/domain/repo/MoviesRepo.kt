package io.mateam.playground2.domain.repo

import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result

interface MoviesRepo {
    suspend fun getPopularMovies(): Result<PopularMovies>
}