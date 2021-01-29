package com.chs.youranimelist.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery

class AnimeRecViewPagerDiffUtilCallBack: DiffUtil.ItemCallback<AnimeRecListQuery.Medium>() {
    override fun areItemsTheSame(oldItem: AnimeRecListQuery.Medium, newItem: AnimeRecListQuery.Medium): Boolean {
        return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
    }

    override fun areContentsTheSame(oldItem: AnimeRecListQuery.Medium, newItem: AnimeRecListQuery.Medium): Boolean {
        return oldItem == newItem
    }
}