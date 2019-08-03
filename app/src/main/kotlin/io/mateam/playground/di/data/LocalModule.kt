@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.local.LocalMoviesDataSource
import io.mateam.playground2.data.dataSource.local.LocalUserDataSource
import io.mateam.playground2.data.dataSource.local.RoomDbMoviesDataSource
import io.mateam.playground2.data.dataSource.local.RoomDbUserDataSource
import io.mateam.playground2.data.dataSource.local.dao.MoviesDao
import io.mateam.playground2.data.dataSource.local.dao.UserDao
import io.mateam.playground2.data.dataSource.local.db.AppDatabase
import io.mateam.playground2.data.dataSource.local.mapper.DbMovieMapper
import io.mateam.playground2.data.dataSource.local.mapper.DbUserMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    //region Mappers
    single<DbMovieMapper> { DbMovieMapper() }
    single<DbUserMapper> { DbUserMapper() }
    //endregion

    //region DataBase and DAO
    single<AppDatabase> { AppDatabase(androidContext()) }

    single<MoviesDao> { get<AppDatabase>().moviesDao() }
    single<UserDao> { get<AppDatabase>().userDao() }
    //endregion

    //region Local DataSources
    single<LocalMoviesDataSource> { RoomDbMoviesDataSource(get<MoviesDao>(), get<DbMovieMapper>()) }
    single<LocalUserDataSource> { RoomDbUserDataSource(get<UserDao>(), get<DbUserMapper>()) }
    //endregion
}

