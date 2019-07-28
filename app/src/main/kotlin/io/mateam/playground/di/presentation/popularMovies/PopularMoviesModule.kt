@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.presentation.popularMovies

import io.mateam.playground.presentation.popular.viewModel.PopularMoviesViewModel
import io.mateam.playground.presentation.popular.viewModel.mapper.PopularMoviesUiMapper
import io.mateam.playground.presentation.popular.viewModel.paginator.PopularMoviesPagination
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val popularMoviesModule = module {
    viewModel<PopularMoviesViewModel> {
        PopularMoviesViewModel(
            get<GetPopularMoviesPage>(),
            PopularMoviesPagination(),
            PopularMoviesUiMapper()
        )
    }
}
