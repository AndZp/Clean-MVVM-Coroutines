package io.mateam.playground.presentation.popular.mapper

import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground2.domain.entity.movie.Movie
import io.mateam.playground2.domain.entity.movie.MovieFullDetails

class MoviesUiMapper {
    fun mapFromMovies(movies: List<Movie>): List<MovieUiModel> {
        return movies.map { movie ->
            MovieUiModel(
                id = movie.id,
                imageUrl = movie.posterPath,
                voteAverage = movie.voteAverage,
                title = movie.title,
                adult = movie.adult,
                releaseData = movie.releaseData,
                originalLanguage = movie.originalLanguage,
                overview = movie.overview
            )
        }
    }

    fun mapFromFullMoviesDetails(movies: List<MovieFullDetails>): List<MovieUiModel> {
        return movies.map { movie ->
            MovieUiModel(
                id = movie.id,
                imageUrl = movie.poster_path,
                voteAverage = movie.vote_average,
                title = movie.title,
                adult = movie.adult,
                releaseData = movie.release_date,
                originalLanguage = movie.original_language,
                overview = movie.overview
            )
        }
    }
}