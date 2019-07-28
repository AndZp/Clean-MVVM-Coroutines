package io.mateam.playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground2.data.utils.logDebug
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val popularMoviesViewModel:PopularMoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
