package io.mateam.playground.presentation.popular.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground.presentation.utils.logDebug
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : Fragment() {
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        popularMoviesViewModel.loadNextPage()
    }


    private fun initViewModel() {
        popularMoviesViewModel.state.observe(this, Observer {
            logDebug("popularMoviesViewModel.state  [${popularMoviesViewModel.state}]")
        })
    }
}
