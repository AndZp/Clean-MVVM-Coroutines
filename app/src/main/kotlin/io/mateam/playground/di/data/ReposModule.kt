@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.cache.InMemoryCacheDataSource
import io.mateam.playground2.data.dataSource.local.LocalMoviesDataSource
import io.mateam.playground2.data.dataSource.local.LocalUserDataSource
import io.mateam.playground2.data.dataSource.local.sharedPref.UserPreferences
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.repo.TmdbMoviesRepo
import io.mateam.playground2.data.repo.UserRepoImpl
import io.mateam.playground2.domain.repo.MoviesRepo
import io.mateam.playground2.domain.repo.UserRepo
import org.koin.dsl.module

val reposModule = module {
    single<MoviesRepo> {
        TmdbMoviesRepo(
            get<RemoteMoviesDataSource>(),
            get<LocalMoviesDataSource>(),
            InMemoryCacheDataSource()
        )
    }

    single<UserRepo> {
        UserRepoImpl(
            get<UserPreferences>(),
            get<LocalUserDataSource>()
        )
    }
}
