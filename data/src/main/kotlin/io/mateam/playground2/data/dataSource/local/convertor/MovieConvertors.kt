package io.mateam.playground2.data.dataSource.local.convertor

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.mateam.playground2.data.dataSource.local.entity.DbGenre
import java.util.*
import com.squareup.moshi.Types
import io.mateam.playground2.data.dataSource.local.entity.DbProductionCompany
import io.mateam.playground2.data.dataSource.local.entity.DbSpokenLanguage


class GenreListConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val genreAdapter: JsonAdapter<List<DbGenre>> = moshi.adapter(Types.newParameterizedType(List::class.java, DbGenre::class.java))

    @TypeConverter
    fun fromJson(data: String?): List<DbGenre>? {
        if (data == null){
            return Collections.emptyList()
        }
        return genreAdapter.fromJson(data)
    }

    @TypeConverter
    fun someObjectListToString(genres: List<DbGenre>?): String? {
        return genreAdapter.toJson(genres)
    }
}

class ProductionCompanyListConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val genreAdapter: JsonAdapter<List<DbProductionCompany>> = moshi.adapter(Types.newParameterizedType(List::class.java, DbProductionCompany::class.java))

    @TypeConverter
    fun fromJson(data: String?): List<DbProductionCompany>? {
        if (data == null){
            return Collections.emptyList()
        }
        return genreAdapter.fromJson(data)
    }

    @TypeConverter
    fun someObjectListToString(companies: List<DbProductionCompany>?): String? {
        return genreAdapter.toJson(companies)
    }
}

class SpokenLanguageListConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val genreAdapter: JsonAdapter<List<DbSpokenLanguage>> = moshi.adapter(Types.newParameterizedType(List::class.java, DbSpokenLanguage::class.java))

    @TypeConverter
    fun fromJson(data: String?): List<DbSpokenLanguage>? {
        if (data == null){
            return Collections.emptyList()
        }
        return genreAdapter.fromJson(data)
    }

    @TypeConverter
    fun someObjectListToString(languages: List<DbSpokenLanguage>?): String? {
        return genreAdapter.toJson(languages)
    }
}