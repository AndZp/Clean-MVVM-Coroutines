package io.mateam.playground.presentation.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mateam.playground.presentation.details.helper.FavoriteStateHelper
import io.mateam.playground2.domain.entity.result.Failure
import io.mateam.playground2.domain.useCase.CheckMovieInFavorite
import io.mateam.playground2.domain.useCase.UpdateUserMovieFavoriteState
import io.mateam.playground2.domain.utils.logDebug
import io.mateam.playground2.domain.utils.logWarning

class FavoriteMoviesViewModel(
    private val movieId: Int,
    private val checkMovieInFavorite: CheckMovieInFavorite,
    private val updateFavoriteState: UpdateUserMovieFavoriteState,
    private val favoriteStateHelper: FavoriteStateHelper
) : ViewModel() {
    val favoriteState = MutableLiveData<FavoriteState>().apply { value = FavoriteState.Unknown }

    init {
        initFavoriteStateHelper()
        loadFavoriteState()
    }

    fun notifyFavoriteClicked() {
        favoriteStateHelper.onFavoriteClick()
    }

    private fun initFavoriteStateHelper() {
        favoriteStateHelper.onChangeToFavorite = {
            postFavoriteState(true)
            updateFavoriteState.invoke(viewModelScope, UpdateUserMovieFavoriteState.Param(movieId, true))
        }
        favoriteStateHelper.onChangeToRegular = {
            updateFavoriteState.invoke(viewModelScope, UpdateUserMovieFavoriteState.Param(movieId, false))
            postFavoriteState(false)
        }
    }

    private fun loadFavoriteState() {
        logDebug("loadFavoriteState: movieId [$movieId]")
        val params = CheckMovieInFavorite.Param(movieId)
        checkMovieInFavorite(viewModelScope, params) {
            it.either(
                ::handleFavoriteStateFailure,
                ::handleFavoriteStateSuccess
            )
        }
    }

    private fun handleFavoriteStateFailure(failure: Failure) {
        logDebug("handleFavoriteStateFailure: [${failure.javaClass.simpleName}]")
        when (failure) {
            is CheckMovieInFavorite.CheckFavoriteStateFailure.LoadError -> favoriteState.postValue(FavoriteState.Unknown)
            else -> logWarning("handleFavoriteStateFailure: Unexpected failure [$failure]")
        }
    }

    private fun handleFavoriteStateSuccess(isInFavorite: Boolean) {
        logDebug("handleFavoriteStateSuccess, movie id = [$movieId, isInFavorite [$isInFavorite")
        favoriteStateHelper.init(isInFavorite)
    }


    private fun postFavoriteState(inFavorite: Boolean) {
        logDebug("postFavoriteState:  inFavorite[$inFavorite]")
        favoriteState.postValue(if (inFavorite) FavoriteState.Favorite else FavoriteState.Regular)
    }
}

sealed class FavoriteState {
    object Favorite : FavoriteState()
    object Regular : FavoriteState()
    object Unknown : FavoriteState()
}