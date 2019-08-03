package io.mateam.playground.presentation.favorite.viewModel

import androidx.lifecycle.*
import io.mateam.playground.presentation.common.entity.UiMoviesState
import io.mateam.playground.presentation.popular.mapper.MoviesUiMapper
import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage
import io.mateam.playground2.domain.useCase.GetUserFavoritesMovies
import io.mateam.playground2.domain.useCase.NoParams
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class UserFavoriteMoviesViewModel(
    private val getUserFavoritesMovies: GetUserFavoritesMovies,
    private val uiMapper: MoviesUiMapper
) : ViewModel(), LifecycleObserver {

    val state = MutableLiveData<UiMoviesState>()

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    private fun loadUserFavorites() {
        logDebug("loadUserFavorites]")
        state.postValue(UiMoviesState.Loading)
        getUserFavoritesMovies(viewModelScope, NoParams) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleFailure(failure: Failure) {
        logDebug("handleFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is GetPopularMoviesPage.GetPopularFailure.LoadError -> state.postValue(UiMoviesState.LoadingError)
            else -> logWarning("handleFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleSuccess(userFavorites: List<MovieFullDetails>) {
        logDebug("handleSuccess, userFavorites size =  [${userFavorites.size}]]")
        postPopularMovies(userFavorites)
    }

    private fun postPopularMovies(movies: List<MovieFullDetails>) {
        val uiMoviesModels = uiMapper.mapFromFullMoviesDetails(movies)
        state.postValue(UiMoviesState.Success(uiMoviesModels))
    }
}