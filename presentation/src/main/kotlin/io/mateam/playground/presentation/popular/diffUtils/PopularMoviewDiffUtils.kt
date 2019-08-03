package io.mateam.playground.presentation.popular.diffUtils

import androidx.recyclerview.widget.DiffUtil
import io.mateam.playground.presentation.common.adapter.MoviesListItem


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
