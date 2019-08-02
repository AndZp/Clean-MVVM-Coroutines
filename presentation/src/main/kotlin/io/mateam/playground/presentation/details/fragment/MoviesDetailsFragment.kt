package io.mateam.playground.presentation.details.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.mateam.playground.presentation.R
import io.mateam.playground.presentation.details.viewModel.MovieDetailsViewModel
import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground.presentation.utils.GlideApp
import io.mateam.playground.presentation.utils.NotNullParcelableArg
import kotlinx.android.synthetic.main.fragment_movies_details.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import kotlinx.android.synthetic.main.layout_movie_detail_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesDetailsFragment : Fragment() {
    private val movie: MovieUiModel by NotNullParcelableArg(MOVIE_MODEL_ARG_KEY)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMovieModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }


    private fun bindMovieModel() {
        detail_header_title.text = movie.title
        detail_header_release.text = movie.releaseData
        detail_header_star.rating = (movie.voteAverage / 2).toFloat()
        detail_body_summary.text = movie.overview
        GlideApp.with(requireContext())
            .load(movie.imageUrl)
            .into(movie_detail_poster)
    }

    private fun initViewModel() {
    }

    companion object {
        private const val MOVIE_MODEL_ARG_KEY = "io.mateam.movie.MOVIE_MODEL"

        fun buildBundle(movie: MovieUiModel) =
            Bundle().apply {
                putParcelable(MOVIE_MODEL_ARG_KEY, movie)
            }
    }
}

