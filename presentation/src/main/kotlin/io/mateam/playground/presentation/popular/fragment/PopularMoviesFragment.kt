package io.mateam.playground.presentation.popular.fragment


import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.common.entity.UiMoviesState
import io.mateam.playground.presentation.common.fragment.MoviesFragment
import io.mateam.playground.presentation.details.fragment.MoviesDetailsFragment
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground.presentation.utils.logDebug
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : MoviesFragment() {
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModel()

    override fun initViewModel() {
        popularMoviesViewModel.state.observe(this, Observer { state ->
            onStateChanged(state)
        })
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
        logDebug("loadMore")
        popularMoviesViewModel.loadNextPage()
    }

    override fun onMovieClicked(movie: MovieUiModel, sharedImageView: ImageView) {
        logDebug("onMovieClicked: movie id [${movie.id}], title [${movie.title}]")
        val transitionName = ViewCompat.getTransitionName(sharedImageView)?:""
        val extras =  FragmentNavigatorExtras(sharedImageView to transitionName)
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
            R.id.action_popularMoviesFragment_to_moviesDetailsFragment,
            MoviesDetailsFragment.buildBundle(movie),
            null,
            extras
        )
    }
}
