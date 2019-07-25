package io.mateam.playground.presentation.popular.viewModel

import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage

class PopularMoviesViewModel(private val getPopularMoviesPage: GetPopularMoviesPage) {

}


sealed class PopularMoviesState {
    object Loading : PopularMoviesState()
    object Empty : PopularMoviesState()
    data class Success(val movies: PopularMovies) : PopularMoviesState()
}