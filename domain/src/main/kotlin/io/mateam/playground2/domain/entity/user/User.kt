package io.mateam.playground2.domain.entity.user

import java.util.*

sealed class User(
    val id: String,
    val favoritesMovies: FavoriteMovies
) {
    companion object {
        fun createAnonymous(): Anonymous {
            return Anonymous(
                id = UUID.randomUUID().toString(),
                favoritesMovies = FavoriteMoviesImpl()
            )
        }
    }
}

class Anonymous(
    id: String,
    favoritesMovies: FavoriteMovies
) : User(id, favoritesMovies)


