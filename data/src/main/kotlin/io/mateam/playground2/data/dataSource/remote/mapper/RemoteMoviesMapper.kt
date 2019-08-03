package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMoviesDetailsResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbPopularMoviesResponse
import io.mateam.playground2.domain.entity.movie.*

class RemoteMoviesMapper {
    fun mapPopular(response: TmdbPopularMoviesResponse): PopularMovies? {
        return PopularMovies(
            page = response.page ?: return null,
            totalMovies = response.total_results ?: return null,
            totalPages = response.total_pages ?: return null,
            movies = response.results?.mapNotNull { tmdbMovie ->
                Movie(
                    id = tmdbMovie.id ?: return@mapNotNull null,
                    title = tmdbMovie.title ?: return@mapNotNull null,
                    voteAverage = tmdbMovie.vote_average ?: return@mapNotNull null,
                    overview = tmdbMovie.overview ?: return@mapNotNull null,
                    adult = tmdbMovie.adult ?: return@mapNotNull null,
                    posterPath = MediaPathParser.getPosterPath(tmdbMovie.poster_path),
                    releaseData = tmdbMovie.release_date ?: return@mapNotNull null,
                    originalLanguage = tmdbMovie.original_language ?: return@mapNotNull null
                )
            } ?: return null
        )
    }

    fun mapFullDetails(response: TmdbMoviesDetailsResponse): MovieFullDetails? {
        return MovieFullDetails(
            id = response.id ?: return null,
            adult = response.adult ?: return null,
            overview = response.overview ?: return null,
            popularity = response.popularity ?: return null,
            title = response.title ?: return null,
            backdrop_path = response.backdrop_path,
            budget = response.budget,
            genres = response.genres?.mapNotNull { mapGenre(it) },
            homepage = response.homepage,
            imdb_id = response.imdb_id,
            original_language = response.original_language,
            original_title = response.original_title,
            poster_path = MediaPathParser.getPosterPath(response.poster_path),
            production_companies = response.production_companies?.mapNotNull { mapProductionCompany(it) },
            release_date = response.release_date,
            revenue = response.revenue,
            runtime = response.runtime,
            spoken_languages = response.spoken_languages?.mapNotNull { mapSpokenLanguage(it) },
            status = response.status,
            tagline = response.tagline,
            vote_average = response.vote_average,
            vote_count = response.vote_count,
            video = response.video
        )
    }

    private fun mapSpokenLanguage(language: io.mateam.playground2.data.dataSource.remote.entity.SpokenLanguage): SpokenLanguage? {
        return SpokenLanguage(
            iso_639_1 = language.iso_639_1 ?: return null,
            name = language.name ?: return null
        )
    }

    private fun mapProductionCompany(company: io.mateam.playground2.data.dataSource.remote.entity.ProductionCompany): ProductionCompany? {
        return ProductionCompany(
            id = company.id ?: return null,
            name = company.name ?: return null,
            logo_path = MediaPathParser.getPosterPath(company.logo_path),
            origin_country = company.origin_country
        )
    }

    private fun mapGenre(genre: io.mateam.playground2.data.dataSource.remote.entity.Genre): Genre? {
        return Genre(
            id = genre.id ?: return null,
            name = genre.name ?: return null
        )
    }
}