package io.mateam.playground2.data.dataSource.remote.mapper

import io.mateam.playground2.data.dataSource.remote.entity.Result
import io.mateam.playground2.data.dataSource.remote.entity.TmdbReviewsResponse
import io.mateam.playground2.domain.entity.movie.MovieReviews
import io.mateam.playground2.domain.entity.movie.Review

class RemoteReviewsMapper {

    fun mapReviews(response: TmdbReviewsResponse): MovieReviews? {
        return MovieReviews(
            id = response.id ?: return null,
            page = response.page ?: return null,
            totalPages = response.total_pages ?: return null,
            totalRevies = response.total_results ?: return null,
            reviews = response.results?.mapNotNull { mapReview(it) } ?: emptyList()
        )
    }

    private fun mapReview(it: Result): Review? {
        return Review(
            author = it.author ?: return null,
            content = it.content ?: return null,
            id = it.id,
            url = it.url
        )
    }
}