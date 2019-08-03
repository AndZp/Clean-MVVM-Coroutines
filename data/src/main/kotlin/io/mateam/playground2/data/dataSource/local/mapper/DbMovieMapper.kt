package io.mateam.playground2.data.dataSource.local.mapper

import io.mateam.playground2.data.dataSource.local.entity.DbGenre
import io.mateam.playground2.data.dataSource.local.entity.MovieDbModel
import io.mateam.playground2.data.dataSource.local.entity.DbProductionCompany
import io.mateam.playground2.data.dataSource.local.entity.DbSpokenLanguage
import io.mateam.playground2.data.dataSource.remote.mapper.MediaPathParser
import io.mateam.playground2.domain.entity.Genre
import io.mateam.playground2.domain.entity.MovieFullDetails
import io.mateam.playground2.domain.entity.ProductionCompany
import io.mateam.playground2.domain.entity.SpokenLanguage

class DbMovieMapper {
    fun mapToDb(movie: MovieFullDetails): MovieDbModel {
        return MovieDbModel(
            adult = movie.adult,
            backdrop_path = movie.backdrop_path,
            budget = movie.budget,
            genres = movie.genres.map { mapToDbGenre(it) },
            homepage = movie.homepage,
            id = movie.id,
            imdb_id = movie.imdb_id,
            original_language = movie.original_language,
            original_title = movie.original_title,
            overview = movie.overview,
            popularity = movie.popularity,
            poster_path = MediaPathParser.getPosterPath(movie.poster_path),
            production_companies = movie.production_companies.map { mapToDbProductionCompany(it) },
            release_date = movie.release_date,
            revenue = movie.revenue,
            runtime = movie.runtime,
            spoken_languages = movie.spoken_languages.map { mapToDbSpokenLanguage(it) },
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            vote_average = movie.vote_average,
            vote_count = movie.vote_count,
            video = movie.video
        )
    }

    private fun mapToDbSpokenLanguage(language: SpokenLanguage): DbSpokenLanguage {
        return DbSpokenLanguage(
            iso_639_1 = language.iso_639_1,
            name = language.name
        )
    }

    private fun mapToDbProductionCompany(company: ProductionCompany): DbProductionCompany {
        return DbProductionCompany(
            id = company.id,
            logo_path = MediaPathParser.getPosterPath(company.logo_path),
            name = company.name,
            origin_country = company.origin_country
        )
    }

    private fun mapToDbGenre(genre: Genre): DbGenre {
        return DbGenre(
            id = genre.id,
            name = genre.name
        )
    }

    fun mapFromDb(movie: MovieDbModel): MovieFullDetails {
        return MovieFullDetails(
            adult = movie.adult,
            backdrop_path = movie.backdrop_path,
            budget = movie.budget,
            genres = movie.genres.map { mapFromDbGenre(it) },
            homepage = movie.homepage,
            id = movie.id,
            imdb_id = movie.imdb_id,
            original_language = movie.original_language,
            original_title = movie.original_title,
            overview = movie.overview,
            popularity = movie.popularity,
            poster_path = MediaPathParser.getPosterPath(movie.poster_path),
            production_companies = movie.production_companies.map { mapFromDbProductionCompany(it) },
            release_date = movie.release_date,
            revenue = movie.revenue,
            runtime = movie.runtime,
            spoken_languages = movie.spoken_languages.map { mapFromDbSpokenLanguage(it) },
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            vote_average = movie.vote_average,
            vote_count = movie.vote_count,
            video = movie.video
        )
    }

    private fun mapFromDbSpokenLanguage(language: DbSpokenLanguage): SpokenLanguage {
        return SpokenLanguage(
            iso_639_1 = language.iso_639_1,
            name = language.name
        )
    }

    private fun mapFromDbProductionCompany(company: DbProductionCompany): ProductionCompany {
        return ProductionCompany(
            id = company.id,
            logo_path = MediaPathParser.getPosterPath(company.logo_path),
            name = company.name,
            origin_country = company.origin_country
        )
    }

    private fun mapFromDbGenre(genre: DbGenre): Genre {
        return Genre(
            id = genre.id,
            name = genre.name
        )
    }
}
