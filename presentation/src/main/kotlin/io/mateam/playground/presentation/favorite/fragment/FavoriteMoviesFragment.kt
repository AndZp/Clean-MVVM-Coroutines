package io.mateam.playground.presentation.favorite.fragment

import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.common.entity.UiMoviesState
import io.mateam.playground.presentation.common.fragment.MoviesFragment
import io.mateam.playground.presentation.details.fragment.MoviesDetailsFragment
import io.mateam.playground.presentation.favorite.viewModel.UserFavoriteMoviesViewModel
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.utils.logDebug
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMoviesFragment : MoviesFragment() {

    private val favoritesMoviesViewModel: UserFavoriteMoviesViewModel by viewModel()

    override fun initViewModel() {
        with(favoritesMoviesViewModel) {
            lifecycle.addObserver(this)
            state.observe(this@FavoriteMoviesFragment, Observer { onStateChanged(it) })
        }
    }

    private fun onStateChanged(state: UiMoviesState?) {
        logDebug("popularMoviesViewModel.fullDetails  [${state?.javaClass?.simpleName}]")
        when (state) {
            is UiMoviesState.Success -> updateMoviesList(state.movies)
            is UiMoviesState.Loading -> showLoading()
            is UiMoviesState.LoadingError -> showLoadingError()
        }
    }

    override fun loadMore() {
        // Currently - do nothing. Pagination for UserFavorites - TBD
    }

    override fun onMovieClicked(movie: MovieUiModel, sharedImageView: ImageView) {
        logDebug("onMovieClicked: movie id [${movie.id}], title [${movie.title}]")
        val extras = FragmentNavigatorExtras(sharedImageView to "imageView")
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
            R.id.action_navigation_favorite_to_moviesDetailsFragment,
            MoviesDetailsFragment.buildBundle(movie),
            null,
            extras
        )
    }
}