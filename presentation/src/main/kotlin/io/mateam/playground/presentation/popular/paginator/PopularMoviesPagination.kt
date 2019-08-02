package io.mateam.playground.presentation.popular.paginator

import io.mateam.playground2.domain.entity.Movie
import io.mateam.playground2.domain.entity.PopularMovies

class PopularMoviesPagination {

    var nextPage = 1
    val allMovies = mutableListOf<Movie>()

    fun onSuccessLoad(moviesPage:PopularMovies) {
        nextPage++
        allMovies.addAll(moviesPage.movies)
    }
}