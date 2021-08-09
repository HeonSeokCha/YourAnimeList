package com.chs.youranimelist.ui.sortedlist

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.fragment.AnimeList

class SortedDiffUtil : DiffUtil.ItemCallback<AnimeListQuery.Data>(){
    override fun areItemsTheSame(
        oldItem: AnimeListQuery.Data,
        newItem: AnimeListQuery.Data
    ): Boolean {
        return if (oldItem.nonSeason.media != null) {

        }
    }

    override fun areContentsTheSame(
        oldItem: AnimeListQuery.Data,
        newItem: AnimeListQuery.Data
    ): Boolean {
        TODO("Not yet implemented")
    }
}