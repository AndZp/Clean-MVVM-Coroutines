package io.mateam.playground2.data.dataSource.remote

import io.mateam.playground2.data.dataSource.remote.api.TmdbApi
import io.mateam.playground2.data.dataSource.remote.entity.TmdbMoviesDetailsResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbPopularMoviesResponse
import io.mateam.playground2.data.dataSource.remote.entity.TmdbReviewsResponse
import io.mateam.playground2.data.dataSource.remote.mapper.RemoteMoviesMapper
import io.mateam.playground2.data.dataSource.remote.mapper.RemoteReviewsMapper
import io.mateam.playground2.data.utils.logWarning
import io.mateam.playground2.domain.entity.MovieFullDetails
import io.mateam.playground2.domain.entity.MovieReviews
import io.mateam.playground2.domain.entity.PopularMovies
import io.mateam.playground2.domain.entity.Result
import retrofit2.Response
import java.io.IOException

interface RemoteMoviesDataSource {
    suspend fun getPopular(page: Int): Result<PopularMovies>
    suspend fun getMovie(id: String): Result<MovieFullDetails>
    suspend fun getReview(id: Int, page: Int): Result<MovieReviews>
}

class TmdbMoviesApiDataSource(
    private val api: TmdbApi,
    private val moviesMapper: RemoteMoviesMapper,
    private val reviewMapper: RemoteReviewsMapper
) : RemoteMoviesDataSource {

    override suspend fun getPopular(page: Int): Result<PopularMovies> {
        return try {
            val response: Response<TmdbPopularMoviesResponse> = api.getPopularMovie(page)
            val tmdbMovieResponse = response.body()
            if (response.isSuccessful && tmdbMovieResponse != null) {
                val popularMovies = moviesMapper.mapPopular(tmdbMovieResponse)
                Result.Success(popularMovies)
            } else {
                Result.Error(IOException("Error Occurred during getting api.getPopularMovie(), ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            logWarning("getPopular: Error Occurred during getting api.getPopularMovie()", e)
            Result.Error((e))
        }
    }

    override suspend fun getMovie(id: String): Result<MovieFullDetails> {
        return try {
            val response: Response<TmdbMoviesDetailsResponse> = api.getMovie(id)
            val tmdbMovieResponse = response.body()
            if (response.isSuccessful && tmdbMovieResponse != null) {
                val movieDetails = moviesMapper.mapFullDetails(tmdbMovieResponse)
                Result.Success(movieDetails)
            } else {
                Result.Error(IOException("Error Occurred during getting api.getMovie(), ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            logWarning("getPopular: Error Occurred during getting api.getMovie()", e)
            Result.Error((e))
        }
    }

    override suspend fun getReview(id: Int, page: Int): Result<MovieReviews> {
        return try {
            val response: Response<TmdbReviewsResponse> = api.getReviews(id, page)
            val reviewsResponse = response.body()
            if (response.isSuccessful && reviewsResponse != null) {
                val movieDetails = reviewMapper.mapReviews(reviewsResponse)
                Result.Success(movieDetails)
            } else {
                Result.Error(IOException("Error Occurred during getting api.getReview(), ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            logWarning("getPopular: Error Occurred during getting api.getReview()", e)
            Result.Error((e))
        }
    }
}