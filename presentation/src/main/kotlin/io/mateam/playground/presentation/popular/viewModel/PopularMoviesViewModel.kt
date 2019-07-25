package io.mateam.playground.presentation.popular.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mateam.playground2.domain.entity.Failure
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage

class PopularMoviesViewModel(private val getPopularMoviesPage: GetPopularMoviesPage):ViewModel() {

    private var nextPage = 1

    val state = MutableLiveData<PopularMoviesState>().apply {
        this.value = PopularMoviesState.Loading
    }

    fun loadNextPage() {
        val params = GetPopularMoviesPage.Param(nextPage)
        getPopularMoviesPage(viewModelScope, params) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleFailure(failure: Failure) {

    }

    private fun handleSuccess(moviesPage: PopularMovies) {
        when {

        }
    }

    private fun mapToPresentation(moviesPage: PopularMovies) {
    }
}


sealed class PopularMoviesState {
    object Loading : PopularMoviesState()
    object Empty : PopularMoviesState()
    data class Success(val movies: PopularMovies) : PopularMoviesState()
}