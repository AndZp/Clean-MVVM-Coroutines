package io.mateam.playground2.domain.repo

import io.mateam.playground2.domain.entity.MovieFullDetails
import io.mateam.playground2.domain.entity.MovieReviews
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result

interface MoviesRepo {
    suspend fun getPopularMovies(page: Int): Result<PopularMovies>

    suspend fun getMovie(id: Int): Result<MovieFullDetails>

    suspend fun getReviews(id: Int, page: Int): Result<MovieReviews>
}