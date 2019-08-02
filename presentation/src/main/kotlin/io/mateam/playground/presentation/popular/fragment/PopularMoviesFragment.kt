package io.mateam.playground.presentation.popular.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.popular.adapter.MoviesAdapter
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesState
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground.presentation.popular.viewModel.entity.MovieUiModel
import io.mateam.playground.presentation.utils.EndlessRecyclerViewScrollListener
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : Fragment() {

    private val popularMoviesViewModel: PopularMoviesViewModel by viewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var paginationListener : EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesRV()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        loadMore()
    }

    private fun initMoviesRV() {
        moviesAdapter = MoviesAdapter(requireContext())
        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        rvMovies.adapter = moviesAdapter
        rvMovies.layoutManager = linearLayoutManager
        rvMovies.itemAnimator = DefaultItemAnimator()
        paginationListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadMore()
            }
        }
        rvMovies.addOnScrollListener(paginationListener)
    }

    private fun initViewModel() {
        popularMoviesViewModel.state.observe(this, Observer { state ->
            onStateChanged(state)
        })
    }

    private fun onStateChanged(state: PopularMoviesState?) {
        logDebug("popularMoviesViewModel.state  [${state?.javaClass?.simpleName}]")
        when (state) {
            is PopularMoviesState.Success -> updateMoviesList(state.movies)
            is PopularMoviesState.Loading -> showLoading()
            is PopularMoviesState.LoadingError -> showLoadingError()
        }
    }

    private fun updateMoviesList(movies: List<MovieUiModel>) {
        logDebug("updateMoviesList: movies size [${movies.size}]")
        hideLoading()
        moviesAdapter.update(movies)
    }

    private fun showLoadingError() {
        logDebug("showLoadingError")
        hideLoading()
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        logDebug("showLoading")
        paginationListener.setLoadingState(true)
        moviesAdapter.addLoadingFooter()
    }

    private fun hideLoading() {
        logDebug("hideLoading")
        moviesAdapter.removeLoadingFooter()
        paginationListener.setLoadingState(false)
    }

    private fun loadMore() {
        logDebug("loadMore")
        popularMoviesViewModel.loadNextPage()
    }
}
