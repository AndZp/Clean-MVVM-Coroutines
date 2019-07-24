package io.mateam.playground2.data.dataSource.remote.api

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovie(@Query(value = "page") page: Int): Response<TmdbMovieResponse>
}