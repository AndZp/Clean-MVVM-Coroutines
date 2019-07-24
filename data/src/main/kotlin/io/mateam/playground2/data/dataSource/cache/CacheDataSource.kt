package io.mateam.playground2.data.dataSource.cache

import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.PopularMovies

interface CacheDataSource {
    fun getPopularMovies(): PopularMovies?
    fun cachePopularMovies(movies:PopularMovies)
    fun clear()
}

class InMemoryCacheDataSource : CacheDataSource {

    private var popularMovies: PopularMovies? = null

    override fun getPopularMovies(): PopularMovies? {
        logDebug("getPopularMovies: return $popularMovies")
        return popularMovies
    }

    override fun cachePopularMovies(movies: PopularMovies) {
        logDebug("addToCache: movies $movies")
        popularMovies = movies
    }

    override fun clear() {
        logDebug("clear was called")
        popularMovies = null
    }
}