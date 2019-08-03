package io.mateam.playground2.data.dataSource.local.convertor

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.util.*
import com.squareup.moshi.Types
import io.mateam.playground2.data.dataSource.local.entity.LoginType


class LoginTypeConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val typeAdapter: JsonAdapter<LoginType> = moshi.adapter(LoginType::class.java)

    @TypeConverter
    fun fromJson(data: String): LoginType? {
      return typeAdapter.fromJson(data)
    }

    @TypeConverter
    fun someObjectListToString(genres: LoginType): String? {
        return typeAdapter.toJson(genres)
    }
}

class IntListConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val typeAdapter: JsonAdapter<List<Int>> = moshi.adapter(Types.newParameterizedType(List::class.java, Integer::class.java))

    @TypeConverter
    fun fromJson(data: String?): List<Int>? {
        if (data == null){
            return Collections.emptyList()
        }
        return typeAdapter.fromJson(data)
    }

    @TypeConverter
    fun someObjectListToString(list: List<Int>?): String? {
        return typeAdapter.toJson(list)
    }
}

