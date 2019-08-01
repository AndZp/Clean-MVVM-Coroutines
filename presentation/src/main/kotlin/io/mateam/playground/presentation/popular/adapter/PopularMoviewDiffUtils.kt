package io.mateam.playground.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import io.mateam.playground.presentation.utils.logDebug


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
        val areItemsTheSame = old[oldItemPosition] == updated[newItemPosition]
        logDebug("areItemsTheSame: oldItemPosition [$oldItemPosition], newItemPosition[$newItemPosition], areItemsTheSame[$areItemsTheSame]")
        return areItemsTheSame
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val areContentsTheSame = old[oldItemPosition] == (updated[newItemPosition])
        logDebug("areContentsTheSame: oldItemPosition [$oldItemPosition], newItemPosition[$newItemPosition], areContentsTheSame[$areContentsTheSame]")

        return areContentsTheSame
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
