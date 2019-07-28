@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.cache.InMemoryCacheDataSource
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.repo.TmdbPopularMoviesRepo
import io.mateam.playground2.domain.repo.PopularMoviesRepo
import org.koin.dsl.module

val moviesRepoModule = module {
    single<PopularMoviesRepo> { TmdbPopularMoviesRepo(get<RemoteMoviesDataSource>(), InMemoryCacheDataSource()) }
}