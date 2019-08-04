package io.mateam.playground2.domain.entity.movie

data class MovieFullDetails(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

data class SpokenLanguage(
    val iso_639_1: String,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String?
)

data class Genre(
    val id: Int,
    val name: String
)