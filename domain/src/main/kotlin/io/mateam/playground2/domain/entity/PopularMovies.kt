package io.mateam.playground2.domain.entity

data class PopularMovies(
    val page: Int,
    val movies: List<Movie>,
    val totalMovies: Int,
    val totalPages: Int
) : List<Movie> by movies