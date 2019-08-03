package io.mateam.playground2.data.dataSource.remote.api

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMoviesDetailsResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbPopularMoviesResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMovie(@Query(value = "page") page: Int): Response<TmdbPopularMoviesResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path(value = "movie_id") id: Int): Response<TmdbMoviesDetailsResponse>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path(value = "movie_id") id: Int,
        @Query(value = "page") page: Int,
        @Query(value = "language") language: String = "en-US"
    ): Response<TmdbReviewsResponse>
}