@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.domain

import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.useCase.GetMovieReview
import io.mateam.playground2.domain.useCase.GetPopularMoviesPage
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetPopularMoviesPage> { GetPopularMoviesPage(get<MoviesRepo>()) }

    factory<GetMovieReview> { GetMovieReview(get<MoviesRepo>()) }
}

