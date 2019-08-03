package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMoviesDetailsResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbPopularMoviesResponse
import io.mateam.playground2.domain.entity.movie.*

class RemoteMoviesMapper {
    fun mapPopular(response: TmdbPopularMoviesResponse): PopularMovies {
        return PopularMovies(
            page = response.page,
            totalMovies = response.total_results,
            totalPages = response.total_pages,
            movies = response.results.map { tmdbMovie ->
                Movie(
                    id = tmdbMovie.id,
                    title = tmdbMovie.title,
                    voteAverage = tmdbMovie.vote_average,
                    overview = tmdbMovie.overview,
                    adult = tmdbMovie.adult,
                    posterPath = MediaPathParser.getPosterPath(tmdbMovie.poster_path),
                    releaseData = tmdbMovie.release_date,
                    originalLanguage = tmdbMovie.original_language
                )
            }
        )
    }

    fun mapFullDetails(response: TmdbMoviesDetailsResponse): MovieFullDetails {
        return MovieFullDetails(
            adult = response.adult,
            backdrop_path = response.backdrop_path,
            budget = response.budget,
            genres = response.genres.map { mapGenre(it) },
            homepage = response.homepage,
            id = response.id,
            imdb_id = response.imdb_id,
            original_language = response.original_language,
            original_title = response.original_title,
            overview = response.overview,
            popularity = response.popularity,
            poster_path = MediaPathParser.getPosterPath(response.poster_path),
            production_companies = response.production_companies.map { mapProductionCompany(it) },
            release_date = response.release_date,
            revenue = response.revenue,
            runtime = response.runtime,
            spoken_languages = response.spoken_languages.map { mapSpokenLanguage(it) },
            status = response.status,
            tagline = response.tagline,
            title = response.title,
            vote_average = response.vote_average,
            vote_count = response.vote_count,
            video = response.video
        )
    }

    private fun mapSpokenLanguage(language: io.mateam.playground2.data.dataSource.remote.entity.SpokenLanguage): SpokenLanguage {
        return SpokenLanguage(
            iso_639_1 = language.iso_639_1,
            name = language.name
        )
    }

    private fun mapProductionCompany(company: io.mateam.playground2.data.dataSource.remote.entity.ProductionCompany): ProductionCompany {
        return ProductionCompany(
            id = company.id,
            logo_path = MediaPathParser.getPosterPath(company.logo_path),
            name = company.name,
            origin_country = company.origin_country
        )
    }

    private fun mapGenre(genre: io.mateam.playground2.data.dataSource.remote.entity.Genre): Genre {
        return Genre(
            id = genre.id,
            name = genre.name
        )
    }
}