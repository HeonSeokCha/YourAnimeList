package com.chs.youranimelist.ui.search.manga

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.SearchMangaQuery

class SearchMangaDiffUtil : DiffUtil.ItemCallback<SearchMangaQuery.Medium>() {
    override fun areItemsTheSame(
        oldItem: SearchMangaQuery.Medium,
        newItem: SearchMangaQuery.Medium
    ): Boolean {
        return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
    }

    override fun areContentsTheSame(
        oldItem: SearchMangaQuery.Medium,
        newItem: SearchMangaQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }
}