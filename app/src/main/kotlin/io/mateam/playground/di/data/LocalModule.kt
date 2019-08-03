@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.local.LocalMoviesDataSource
import io.mateam.playground2.data.dataSource.local.RoomDbMoviesDataSource
import io.mateam.playground2.data.dataSource.local.dao.MoviesDao
import io.mateam.playground2.data.dataSource.local.db.AppDatabase
import io.mateam.playground2.data.dataSource.local.mapper.DbMovieMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    single<DbMovieMapper> { DbMovieMapper() }

    single<AppDatabase> { AppDatabase(androidContext()) }

    single<MoviesDao> { get<AppDatabase>().moviesDao() }

    single<LocalMoviesDataSource> { RoomDbMoviesDataSource(get<MoviesDao>(), get<DbMovieMapper>()) }
}

