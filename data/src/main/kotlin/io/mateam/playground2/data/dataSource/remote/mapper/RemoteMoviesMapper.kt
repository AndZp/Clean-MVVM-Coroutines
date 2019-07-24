package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovieResponse
import io.mateam.playground2.domain.entity.Movie
import io.mateam.playground2.domain.entity.PopularMovies

class RemoteMoviesMapper {
    fun map(response: TmdbMovieResponse): PopularMovies {
        return PopularMovies(
            page = response.page,
            totalMovies = response.totalMovies,
            totalPages = response.totalPages,
            movies = response.movies.map { tmdbMovie ->
                Movie(
                    id = tmdbMovie.id,
                    title = tmdbMovie.title,
                    voteAverage = tmdbMovie.voteAverage,
                    overview = tmdbMovie.overview,
                    adult = tmdbMovie.adult
                )
            }
        )
    }
}