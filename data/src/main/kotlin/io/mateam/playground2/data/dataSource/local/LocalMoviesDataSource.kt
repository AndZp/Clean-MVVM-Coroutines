package io.mateam.playground2.data.dataSource.local

import io.mateam.playground2.data.dataSource.local.dao.MoviesDao
import io.mateam.playground2.data.dataSource.local.mapper.DbMovieMapper
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.MovieFullDetails
import io.mateam.playground2.domain.entity.Result
import java.io.IOException

interface LocalMoviesDataSource {
    suspend fun insertMovie(movie: MovieFullDetails)
    suspend fun getMovie(id: Int): Result<MovieFullDetails>
}

class RoomDbMoviesDataSource(private val db: MoviesDao, private val mapper: DbMovieMapper) : LocalMoviesDataSource {

    override suspend fun insertMovie(movie: MovieFullDetails) {
        logDebug("insertMovie: movie id [${movie.id}]")
        val dbModel = mapper.mapToDb(movie)
        db.insertMovie(dbModel)
    }

    override suspend fun getMovie(id: Int): Result<MovieFullDetails> {
        logDebug("getMovie: movie id [$id]")
        val dbResponse = db.getMovieById(id)
        return if (dbResponse.isEmpty()) {
            logDebug("getMovie: movie id [$id] can not be found. Return Result.Error")
            Result.Error(IOException("Movie with ID [$id] can not be found"))
        } else {
            val movieFullDetails = mapper.mapFromDb(dbResponse.first())
            logDebug("getMovie: movie id [$id] found in DB. Return Result.Success")
            Result.Success(movieFullDetails)
        }
    }
}