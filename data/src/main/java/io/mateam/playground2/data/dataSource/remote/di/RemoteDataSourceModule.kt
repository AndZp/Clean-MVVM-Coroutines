@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground2.data.dataSource.remote.di

import io.mateam.playground2.data.BuildConfig
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.dataSource.remote.TmdbMoviesApiDataSource
import io.mateam.playground2.data.dataSource.remote.api.TmdbApi
import io.mateam.playground2.data.dataSource.remote.interceptor.TmdbAuthInterceptor
import io.mateam.playground2.data.dataSource.remote.mapper.RemoteMoviesMapper
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteDataSourceModule = module {
    factory<TmdbAuthInterceptor> { TmdbAuthInterceptor(BuildConfig.TMDB_API_KEY) }

    factory<OkHttpClient> {
        OkHttpClient().newBuilder()
            .addInterceptor(get<TmdbAuthInterceptor>())
            .build()
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.TMBD_BASE_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    factory<TmdbApi> { get<Retrofit>().create(TmdbApi::class.java) }

    factory<RemoteMoviesMapper> { RemoteMoviesMapper() }

    single<RemoteMoviesDataSource> { TmdbMoviesApiDataSource(get<TmdbApi>(), get<RemoteMoviesMapper>()) }
}

