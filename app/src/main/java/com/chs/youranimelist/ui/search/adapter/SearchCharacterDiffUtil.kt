package com.chs.youranimelist.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchCharacterQuery

class SearchCharacterDiffUtil : DiffUtil.ItemCallback<SearchCharacterQuery.Character>() {
    override fun areItemsTheSame(
        oldItem: SearchCharacterQuery.Character,
        newItem: SearchCharacterQuery.Character
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SearchCharacterQuery.Character,
        newItem: SearchCharacterQuery.Character
    ): Boolean {
        return oldItem == newItem
    }
}