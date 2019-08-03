package io.mateam.playground2.data.dataSource.remote.entity

data class TmdbReviewsResponse(
    val id: Int?,
    val page: Int?,
    val results: List<Result>?,
    val total_pages: Int?,
    val total_results: Int?
)

data class Result(
    val author: String?,
    val content: String?,
    val id: String?,
    val url: String?
)