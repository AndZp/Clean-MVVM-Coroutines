package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovie
import io.mateam.playground2.domain.entity.Movie
import io.mateam.playground2.domain.entity.PopularMovies

class RemoteMoviesMapper {
    fun map(movies:List<TmdbMovie>): PopularMovies {
        return PopularMovies(
            movies = movies.map { tmdbMovie ->
                Movie(
                    id = tmdbMovie.id,
                    title = tmdbMovie.title,
                    vote_average = tmdbMovie.vote_average,
                    overview = tmdbMovie.overview,
                    adult = tmdbMovie.adult
                )
            }
        )
    }
}