@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.presentation.popularMovies

import io.mateam.playground.presentation.details.helper.FavoriteStateHelper
import io.mateam.playground.presentation.details.mapper.MoviesDetailsUiMapper
import io.mateam.playground.presentation.details.viewModel.MovieDetailsViewModel
import io.mateam.playground.presentation.details.viewModel.MovieFavoriteStateViewModel
import io.mateam.playground.presentation.favorite.viewModel.UserFavoriteMoviesViewModel
import io.mateam.playground.presentation.popular.mapper.MoviesUiMapper
import io.mateam.playground.presentation.popular.paginator.PaginationHelper
import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground2.domain.entity.movie.Movie
import io.mateam.playground2.domain.useCase.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel<PopularMoviesViewModel> {
        PopularMoviesViewModel(
            get<GetPopularMoviesPage>(),
            PaginationHelper<Movie>(),
            MoviesUiMapper()
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

    viewModel<MovieFavoriteStateViewModel> { (movieId: Int) ->
        MovieFavoriteStateViewModel(
            movieId,
            get<CheckMovieInFavorite>(),
            get<UpdateUserMovieFavoriteState>(),
            FavoriteStateHelper()
        )
    }

    viewModel<UserFavoriteMoviesViewModel> {
        UserFavoriteMoviesViewModel(
            get<GetUserFavoritesMovies>(),
            MoviesUiMapper()
        )
    }
}
