package io.mateam.playground2.domain.entity.user

interface FavoriteMovies : List<Int> {
    fun add(movieId: Int)
    fun remove(movieId: Int)
    fun isFavorite(movieId: Int): Boolean
}

class FavoriteMoviesImpl(
    private val favorites: MutableList<Int> = mutableListOf()
) : FavoriteMovies, List<Int> by favorites {

    override fun add(movieId: Int) {
        if (!favorites.contains(movieId)) {
            favorites.add(movieId)
        }
    }

    override fun remove(movieId: Int) {
        if (favorites.contains(movieId)) {
            favorites.remove(movieId)
        }
    }

    override fun isFavorite(movieId: Int): Boolean {
        return contains(movieId)
    }
}