package io.mateam.playground2.domain.entity.movie

data class Movie(
    val id: Int,
    val posterPath: String?,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val releaseData: String,
    val originalLanguage: String
)


