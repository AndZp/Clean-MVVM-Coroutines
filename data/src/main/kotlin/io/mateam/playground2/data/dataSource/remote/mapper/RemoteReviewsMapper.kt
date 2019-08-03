package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.Result
import io.mateam.playground2.data.dataSource.remote.entity.TmdbReviewsResponse
import io.mateam.playground2.domain.entity.movie.MovieReviews
import io.mateam.playground2.domain.entity.movie.Review

class RemoteReviewsMapper {

    fun mapReviews(response: TmdbReviewsResponse): MovieReviews {
        return MovieReviews(
            id = response.id,
            page = response.page,
            totalPages = response.total_pages,
            totalRevies = response.total_results,
            reviews = response.results.map {
                mapReview(it)
            }
        )
    }

    private fun mapReview(it: Result): Review {
        return Review(
            author = it.author,
            content = it.content,
            id = it.id,
            url = it.url
        )
    }
}