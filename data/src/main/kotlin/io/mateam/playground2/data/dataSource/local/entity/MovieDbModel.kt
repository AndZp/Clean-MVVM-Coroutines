package io.mateam.playground2.data.dataSource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.mateam.playground2.data.dataSource.local.convertor.GenreListConverter
import io.mateam.playground2.data.dataSource.local.convertor.ProductionCompanyListConverter
import io.mateam.playground2.data.dataSource.local.convertor.SpokenLanguageListConverter

@Entity
data class MovieDbModel(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    @TypeConverters(GenreListConverter::class)
    val genres: List<DbGenre>,
    val homepage: String?,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    @TypeConverters(ProductionCompanyListConverter::class)
    val production_companies: List<DbProductionCompany>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    @TypeConverters(SpokenLanguageListConverter::class)
    val spoken_languages: List<DbSpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

@Entity
data class DbSpokenLanguage(
    @PrimaryKey
    val iso_639_1: String,
    val name: String
)

@Entity
data class DbProductionCompany(
    @PrimaryKey
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

@Entity
data class DbGenre(
    @PrimaryKey
    val id: Int,
    val name: String
)