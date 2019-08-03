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


interface FavoriteMovies : List<Int> {
    fun add(movieId: Int)
    fun remove(movieId: Int)
    fun isFavorite(movieId: Int): Boolean
}

class FavoriteMoviesImpl(
    private val favorites: MutableList<Int> = mutableListOf()
) : FavoriteMovies, List<Int> by favorites {

    override fun add(movieId: Int) {
        favorites.add(movieId)
    }

    override fun remove(movieId: Int) {
        favorites.add(movieId)
    }

    override fun isFavorite(movieId: Int): Boolean {
        return contains(movieId)
    }

}