@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.presentation.popularMovies

import io.mateam.playground.presentation.details.mapper.MoviesDetailsUiMapper
import io.mateam.playground.presentation.details.viewModel.MovieDetailsViewModel
import io.mateam.playground.presentation.popular.mapper.PopularMoviesUiMapper
import io.mateam.playground.presentation.popular.paginator.PaginationHelper
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground2.domain.entity.Movie
import io.mateam.playground2.domain.useCase.GetMovieReview
import io.mateam.playground2.domain.useCase.GetMoviesFullDetails
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val popularMoviesModule = module {
    viewModel<PopularMoviesViewModel> {
        PopularMoviesViewModel(
            get<GetPopularMoviesPage>(),
            PaginationHelper<Movie>(),
            PopularMoviesUiMapper()
        )
    }

    viewModel<MovieDetailsViewModel> { (movieId: Int) ->
        MovieDetailsViewModel(
            movieId,
            get<GetMovieReview>(),
            get<GetMoviesFullDetails>(),
            MoviesDetailsUiMapper(),
            PaginationHelper()
        )
    }
}
