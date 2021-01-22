package com.chs.youranimelist.adapter

import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeDetailQuery

class CharaDiffUtilCallBack: DiffUtil.ItemCallback<AnimeDetailQuery.Node>(){
    override fun areItemsTheSame(
        oldItem: AnimeDetailQuery.Node,
        newItem: AnimeDetailQuery.Node
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AnimeDetailQuery.Node,
        newItem: AnimeDetailQuery.Node
    ): Boolean {
        return oldItem == newItem
    }
}