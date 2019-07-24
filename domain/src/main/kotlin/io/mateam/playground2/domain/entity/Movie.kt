package io.mateam.playground2.domain.entity

data class Movie(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val adult: Boolean
)


