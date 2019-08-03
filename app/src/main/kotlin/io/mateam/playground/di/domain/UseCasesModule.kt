@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.domain

import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.useCase.*
import org.koin.dsl.module

val useCasesModule = module {
    factory<GetPopularMoviesPage> { GetPopularMoviesPage(get<MoviesRepo>()) }

    factory<GetMoviesFullDetails> { GetMoviesFullDetails(get<MoviesRepo>()) }

    factory<GetMovieReview> { GetMovieReview(get<MoviesRepo>()) }

    factory<CheckMovieInFavorite> { CheckMovieInFavorite(get<UserManager>()) }

    factory<UpdateUserMovieFavoriteState> { UpdateUserMovieFavoriteState(get<UserManager>()) }
}

