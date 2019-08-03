package io.mateam.playground.presentation.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.common.adapter.MoviesAdapter
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.utils.EndlessRecyclerViewScrollListener
import io.mateam.playground.presentation.utils.logDebug
import kotlinx.android.synthetic.main.fragment_popular_movies.*

abstract class MoviesFragment : Fragment() {
    protected lateinit var moviesAdapter: MoviesAdapter
    private lateinit var paginationListener: EndlessRecyclerViewScrollListener

    protected abstract fun initViewModel()


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
        moviesAdapter =
            MoviesAdapter(requireContext(), this::onMovieClicked)

        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        paginationListener = buildPaginationScrollListener(linearLayoutManager)
        with(rvMovies) {
            adapter = moviesAdapter
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            addOnScrollListener(paginationListener)
        }
    }

    private fun buildPaginationScrollListener(layoutManager: LinearLayoutManager): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadMore()
            }
        }
    }

    protected fun updateMoviesList(movies: List<MovieUiModel>) {
        logDebug("updateMoviesList: movies size [${movies.size}]")
        hideLoading()
        moviesAdapter.update(movies)
    }

    protected fun showLoadingError() {
        logDebug("showLoadingError")
        hideLoading()
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }

    protected fun showLoading() {
        logDebug("showLoading")
        paginationListener.setLoadingState(true)
        moviesAdapter.addLoadingFooter()
    }

    protected fun hideLoading() {
        logDebug("hideLoading")
        moviesAdapter.removeLoadingFooter()
        paginationListener.setLoadingState(false)
    }

    protected abstract fun loadMore()

    protected abstract fun onMovieClicked(movie: MovieUiModel)
}