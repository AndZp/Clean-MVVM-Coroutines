package io.mateam.playground2.data.repo

import io.mateam.playground2.data.dataSource.cache.CacheDataSource
import io.mateam.playground2.data.dataSource.local.LocalMoviesDataSource
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.movie.MovieFullDetails
import io.mateam.playground2.domain.entity.movie.MovieReviews
import io.mateam.playground2.domain.entity.movie.PopularMovies
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.repo.MoviesRepo

class TmdbMoviesRepo(
    private val remote: RemoteMoviesDataSource,
    private val local: LocalMoviesDataSource,
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
        return when (val dbResult = local.getMovie(id)){
            is Result.Success -> {
                logDebug("getMovie: id [$id]: return movie from DB")
                dbResult
            }
            is Result.Error -> {
                logDebug("getMovie: id [$id]: return movie from Remote")
                return remote.getMovie(id).apply {
                    if (this is Result.Success)
                        local.insertMovie(this.data)
                }
            }
        }
    }

    override suspend fun getReviews(id: Int, page: Int): Result<MovieReviews> {
        return remote.getReview(id, page)
    }
}