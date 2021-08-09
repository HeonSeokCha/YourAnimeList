package com.chs.youranimelist.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.network.response.SearchResult

class SearchDiffUtil : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        if (oldItem.animeSearchResult != null && newItem.animeSearchResult != null) {
            oldItem.animeSearchResult!!.fragments.animeList.id == newItem.animeSearchResult!!.fragments.animeList.id
        } else if (oldItem.mangaSearchResult != null && newItem.mangaSearchResult != null) {
            oldItem.mangaSearchResult!!.fragments.animeList.id == newItem.mangaSearchResult!!.fragments.animeList.id
        } else {
            oldItem.charactersSearchResult!!.id == newItem.charactersSearchResult!!.id
        }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean =
        if (oldItem.animeSearchResult != null && newItem.animeSearchResult != null) {
            oldItem.animeSearchResult!!.fragments.animeList == newItem.animeSearchResult!!.fragments.animeList
        } else if (oldItem.mangaSearchResult != null && newItem.mangaSearchResult != null) {
            oldItem.mangaSearchResult!!.fragments.animeList == newItem.mangaSearchResult!!.fragments.animeList
        } else {
            oldItem.charactersSearchResult!! == newItem.charactersSearchResult!!
        }
}