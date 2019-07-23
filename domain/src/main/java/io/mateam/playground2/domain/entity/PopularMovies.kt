package io.mateam.playground2.domain.entity

data class PopularMovies(private val movies: List<Movie>) : List<Movie> by movies