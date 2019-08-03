package io.mateam.playground2.domain.repo

import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.movie.MovieReviews
import io.mateam.playground2.domain.entity.movie.PopularMovies
import io.mateam.playground2.domain.entity.result.Result

interface MoviesRepo {
    suspend fun getPopularMovies(page: Int): Result<PopularMovies>

    suspend fun getMovie(id: Int): Result<MovieFullDetails>

    suspend fun getReviews(id: Int, page: Int): Result<MovieReviews>
}