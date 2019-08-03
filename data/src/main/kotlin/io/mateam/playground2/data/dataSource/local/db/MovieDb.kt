package io.mateam.playground2.data.dataSource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.mateam.playground2.data.dataSource.local.convertor.GenreListConverter
import io.mateam.playground2.data.dataSource.local.convertor.ProductionCompanyListConverter
import io.mateam.playground2.data.dataSource.local.convertor.SpokenLanguageListConverter
import io.mateam.playground2.data.dataSource.local.dao.MoviesDao
import io.mateam.playground2.data.dataSource.local.entity.DbGenre
import io.mateam.playground2.data.dataSource.local.entity.MovieDbModel
import io.mateam.playground2.data.dataSource.local.entity.DbProductionCompany
import io.mateam.playground2.data.dataSource.local.entity.DbSpokenLanguage

@Database(
    entities = [MovieDbModel::class, DbGenre::class, DbSpokenLanguage::class, DbProductionCompany::class],
    version = 1
)
@TypeConverters(
    GenreListConverter::class,
    ProductionCompanyListConverter::class,
    SpokenLanguageListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "movies.db")
            .build()
    }
}