package io.mateam.playground.presentation.popular.paginator

class PaginationHelper<T> {

    var nextPage = 1
    val allItems = mutableListOf<T>()

    fun onSuccessLoad(pageData: List<T>) {
        nextPage++
        allItems.addAll(pageData)
    }
}