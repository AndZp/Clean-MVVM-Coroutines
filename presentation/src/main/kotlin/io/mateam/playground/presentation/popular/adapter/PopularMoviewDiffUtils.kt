package io.mateam.playground.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil


class MovieListItemsDiffCallback(
    private var updated: List<MoviesListItem>,
    private var old: List<MoviesListItem>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return updated.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == updated[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == (updated[newItemPosition])
    }
}
