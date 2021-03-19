package com.chs.youranimelist.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.network.SearchResult

class SearchAnimeDiffUtil : DiffUtil.ItemCallback<SearchAnimeQuery.Medium?>() {
    override fun areItemsTheSame(
        oldItem: SearchAnimeQuery.Medium,
        newItem: SearchAnimeQuery.Medium
    ): Boolean {
        return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
    }

    override fun areContentsTheSame(
        oldItem: SearchAnimeQuery.Medium,
        newItem: SearchAnimeQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }
}