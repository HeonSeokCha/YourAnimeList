package com.chs.youranimelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery

class ViewPagerDiffUtilCallBack: DiffUtil.ItemCallback<AnimeRecListQuery.Medium>() {
    override fun areItemsTheSame(oldItem: AnimeRecListQuery.Medium, newItem: AnimeRecListQuery.Medium): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeRecListQuery.Medium, newItem: AnimeRecListQuery.Medium): Boolean {
        return oldItem == newItem
    }
}