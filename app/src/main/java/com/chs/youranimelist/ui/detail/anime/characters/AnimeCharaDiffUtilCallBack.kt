package com.chs.youranimelist.ui.detail.anime.characters

import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeDetailQuery

class AnimeCharaDiffUtilCallBack: DiffUtil.ItemCallback<AnimeDetailQuery.Node1>(){
    override fun areItemsTheSame(
        oldItem: AnimeDetailQuery.Node1,
        newItem: AnimeDetailQuery.Node1
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AnimeDetailQuery.Node1,
        newItem: AnimeDetailQuery.Node1
    ): Boolean {
        return oldItem == newItem
    }
}