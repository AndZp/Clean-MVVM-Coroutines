package io.mateam.playground.presentation.details.mapper

import io.mateam.playground.presentation.popular.entity.MovieUiModel
import io.mateam.playground2.domain.entity.Movie

class MoviesDetailsUiMapper {
    fun map(movies: List<Movie>): List<MovieUiModel> {
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
}