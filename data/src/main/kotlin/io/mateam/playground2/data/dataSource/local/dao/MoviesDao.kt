package io.mateam.playground2.data.dataSource.local.dao

import androidx.room.*
import io.mateam.playground2.data.dataSource.local.entity.MovieDbModel

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDbModel)

    @Update
    suspend fun updateMovie(Movie: MovieDbModel)

    @Delete
    suspend fun deleteMovie(Movie: MovieDbModel)

    @Query("SELECT * FROM MovieDbModel WHERE id == :id")
    suspend fun getMovieById(id: Int): List<MovieDbModel>

    @Query("SELECT * FROM MovieDbModel")
    suspend fun getMovies(): List<MovieDbModel>
}