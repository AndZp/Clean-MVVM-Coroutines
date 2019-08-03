@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.cache.InMemoryCacheDataSource
import io.mateam.playground2.data.dataSource.local.LocalMoviesDataSource
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.repo.TmdbMoviesRepo
import io.mateam.playground2.domain.repo.MoviesRepo
import org.koin.dsl.module

val moviesRepoModule = module {
    single<MoviesRepo> {
        TmdbMoviesRepo(
            get<RemoteMoviesDataSource>(),
            get<LocalMoviesDataSource>(),
            InMemoryCacheDataSource()
        )
    }
}
