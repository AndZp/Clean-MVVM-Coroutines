package io.mateam.playground2.data.repo

import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.data.dataSource.cache.CacheDataSource
import io.mateam.playground2.data.dataSource.remote.RemoteMoviesDataSource
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.repo.PopularMoviesRepo

class TmdbPopularMoviesRepo(
    private val remote: RemoteMoviesDataSource,
    private val cache: CacheDataSource
) : PopularMoviesRepo {

    override suspend fun getPopularMovies(page:Int): Result<PopularMovies> {
        val cachedMovies = cache.getPopularMovies(page)
        return if (cachedMovies !=null) {
            logDebug("getPopularMovies: return cache")
            Result.Success(cachedMovies)
        } else {
           val  result = remote.fetchPopular(page)
            if (result is Result.Success){
                cache.cachePopularMovies(result.data)
            }
            result
        }
    }
}