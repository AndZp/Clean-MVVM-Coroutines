package io.mateam.playground2.data.dataSource.remote.api

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovie(): Response<TmdbMovieResponse>
}