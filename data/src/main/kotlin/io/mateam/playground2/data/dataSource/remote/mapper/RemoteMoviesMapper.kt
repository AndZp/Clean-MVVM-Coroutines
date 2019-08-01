package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovieResponse
import io.mateam.playground2.domain.entity.Movie
import io.mateam.playground2.domain.entity.PopularMovies

class RemoteMoviesMapper {
    fun map(response: TmdbMovieResponse): PopularMovies {
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
}