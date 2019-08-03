package io.mateam.playground2.data.dataSource.local.mapper

import io.mateam.playground2.data.dataSource.local.entity.LoginType
import io.mateam.playground2.data.dataSource.local.entity.UserDbModel
import io.mateam.playground2.domain.entity.user.Anonymous
import io.mateam.playground2.domain.entity.user.FavoriteMoviesImpl
import io.mateam.playground2.domain.entity.user.User

class DbUserMapper {
    fun mapToDb(user: User): UserDbModel {
        return UserDbModel(
            id = user.id,
            loginType = getLoginType(user),
            favoriteMovies = user.favoritesMovies
        )
    }

    fun mapFromDb(user: UserDbModel): User {
        return when (user.loginType) {
            LoginType.ANONYMOUS -> Anonymous(user.id, FavoriteMoviesImpl(user.favoriteMovies.toMutableList()))
            else -> throw IllegalArgumentException("mapFromDb: The type [user.loginType] currently is not supported")
        }
    }

    private fun getLoginType(user: User): LoginType {
        return when (user) {
            is Anonymous -> LoginType.ANONYMOUS
        }
    }


}
