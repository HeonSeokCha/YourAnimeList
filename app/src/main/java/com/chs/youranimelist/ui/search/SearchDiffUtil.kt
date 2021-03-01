package com.chs.youranimelist.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.fragment.AnimeList

class SearchDiffUtil : DiffUtil.ItemCallback<AnimeList>() {
    override fun areItemsTheSame(oldItem: AnimeList, newItem: AnimeList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeList, newItem: AnimeList): Boolean {
        return oldItem == newItem
    }
}