package io.mateam.playground2.data.dataSource.remote.entity

import com.squareup.moshi.Json

data class TmdbMovieResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val movies: List<TmdbMovie>,
    @Json(name = "total_results") val totalMovies: Int,
    @Json(name = "total_pages") val totalPages: Int
)