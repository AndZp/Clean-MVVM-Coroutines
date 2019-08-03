package io.mateam.playground2.domain.entity.movie

data class MovieReviews(
    val id: Int,
    val page: Int,
    val reviews: List<Review>,
    val totalPages: Int,
    val totalRevies: Int
)

data class Review(
    val author: String,
    val content: String,
    val id: String,
    val url: String
)