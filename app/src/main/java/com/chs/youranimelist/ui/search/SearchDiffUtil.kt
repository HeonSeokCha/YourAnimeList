package com.chs.youranimelist.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.MediaSearchQuery
import com.chs.youranimelist.fragment.AnimeList

class SearchDiffUtil : DiffUtil.ItemCallback<MediaSearchQuery.Medium>() {
    override fun areItemsTheSame(
        oldItem: MediaSearchQuery.Medium,
        newItem: MediaSearchQuery.Medium
    ): Boolean {
        return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
    }

    override fun areContentsTheSame(
        oldItem: MediaSearchQuery.Medium,
        newItem: MediaSearchQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }
}