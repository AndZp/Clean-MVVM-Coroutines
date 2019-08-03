package io.mateam.playground.presentation.common.entity

import io.mateam.playground.presentation.popular.entity.MovieUiModel

sealed class UiMoviesState {
    object Loading : UiMoviesState()
    object LoadingError : UiMoviesState()
    data class Success(val movies: List<MovieUiModel>) : UiMoviesState()
}