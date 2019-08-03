package io.mateam.playground2.data.dataSource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.mateam.playground2.data.dataSource.local.convertor.LoginTypeConverter

@Entity
class UserDbModel(
    @PrimaryKey
    val id: String,
    @TypeConverters(LoginTypeConverter::class)
    val loginType: LoginType,
    val favoriteMovies: List<Int>
)


enum class LoginType {
    ANONYMOUS, LOGGED_IN
}