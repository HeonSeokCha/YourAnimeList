package com.chs.youranimelist.ui.sortedlist

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.fragment.AnimeList

class SortedDiffUtil : DiffUtil.ItemCallback<AnimeList>() {
    override fun areItemsTheSame(
        oldItem: AnimeList,
        newItem: AnimeList
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AnimeList,
        newItem: AnimeList
    ): Boolean {
        return oldItem == newItem
    }
}