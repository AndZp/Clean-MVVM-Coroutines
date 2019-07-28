package io.mateam.playground2.data.dataSource.remote

import io.mateam.playground2.domain.entity.Result
import io.mateam.playground2.data.dataSource.remote.api.TmdbApi
import io.mateam.playground2.data.dataSource.remote.entity.TmdbMovieResponse
import io.mateam.playground2.data.dataSource.remote.mapper.RemoteMoviesMapper
import io.mateam.playground2.data.utils.logWarning
import io.mateam.playground2.domain.entity.PopularMovies
import retrofit2.Response
import java.io.IOException

interface RemoteMoviesDataSource {
    suspend fun fetchPopular(page: Int): Result<PopularMovies>
}

class TmdbMoviesApiDataSource(
    private val api: TmdbApi,
    private val mapper: RemoteMoviesMapper
) : RemoteMoviesDataSource {

    override suspend fun fetchPopular(page: Int): Result<PopularMovies> {
        return try {
            val response: Response<TmdbMovieResponse> = api.getPopularMovie(page)
            val tmdbMovieResponse = response.body()
            if (response.isSuccessful && tmdbMovieResponse != null) {
                val popularMovies = mapper.map(tmdbMovieResponse)
                Result.Success(popularMovies)
            } else {
                Result.Error(IOException("Error Occurred during getting api.getPopularMovie(), ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            logWarning("fetchPopular: Error Occurred during getting api.getPopularMovie()", e)
            Result.Error((e))
        }
    }
}