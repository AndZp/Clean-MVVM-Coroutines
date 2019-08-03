package io.mateam.playground2.data.repo

import io.mateam.playground2.data.dataSource.cache.CacheDataSource
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.MovieFullDetails
import io.mateam.playground2.domain.entity.MovieReviews
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.domain.repo.MoviesRepo

class TmdbMoviesRepo(
    private val remote: RemoteMoviesDataSource,
    private val cache: CacheDataSource
) : MoviesRepo {

    override suspend fun getPopularMovies(page: Int): Result<PopularMovies> {
        val cachedMovies = cache.getPopularMovies(page)
        return if (cachedMovies != null) {
            logDebug("getPopularMovies: return cache")
            Result.Success(cachedMovies)
        } else {
            val result = remote.getPopular(page)
            if (result is Result.Success) {
                cache.cachePopularMovies(result.data)
            }
            result
        }
    }

    override suspend fun getMovie(id: Int): Result<MovieFullDetails> {
        //TBD: local caching in DB
        return remote.getMovie(id)
    }

    override suspend fun getReviews(id: Int, page: Int): Result<MovieReviews> {
        return remote.getReview(id, page)
    }
}