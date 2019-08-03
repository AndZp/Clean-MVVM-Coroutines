package io.mateam.playground.presentation.popular.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.popular.mapper.PopularMoviesUiMapper
import io.mateam.playground.presentation.popular.paginator.PaginationHelper
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.entity.movie.Movie
import io.mateam.playground2.domain.entity.movie.PopularMovies
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class PopularMoviesViewModel(
    private val getPopularMoviesPage: GetPopularMoviesPage,
    private val paginationHelper: PaginationHelper<Movie>,
    private val uiMapper: PopularMoviesUiMapper
) : ViewModel() {

    val state = MutableLiveData<PopularMoviesState>()

    fun loadNextPage() {
        logDebug("loadNextPage: paginationHelper.nextPage [${paginationHelper.nextPage}]")
        state.postValue(PopularMoviesState.Loading)
        val params = GetPopularMoviesPage.Param(paginationHelper.nextPage)
        getPopularMoviesPage(viewModelScope, params) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleFailure(failure: Failure) {
        logDebug("handleFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is GetPopularMoviesPage.GetPopularFailure.LoadError -> state.postValue(PopularMoviesState.LoadingError)
            else -> logWarning("handleFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleSuccess(moviesPage: PopularMovies) {
        logDebug("handleSuccess, moviesPage page =  [${moviesPage.page}, moviesSize = [${moviesPage.movies.size}]]")
        paginationHelper.onSuccessLoad(moviesPage)
        postPopularMovies(paginationHelper.allItems)
    }

    private fun postPopularMovies(allMovies: MutableList<Movie>) {
        val uiMoviesModels = uiMapper.map(allMovies)
        state.postValue(PopularMoviesState.Success(uiMoviesModels))
    }
}

sealed class PopularMoviesState {
    object Loading : PopularMoviesState()
    object LoadingError : PopularMoviesState()
    data class Success(val movies: List<MovieUiModel>) : PopularMoviesState()
}