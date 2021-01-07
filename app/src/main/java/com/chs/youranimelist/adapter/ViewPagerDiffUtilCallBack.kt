package com.chs.youranimelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery

class ViewPagerDiffUtilCallBack: DiffUtil.ItemCallback<AnimeListQuery.Medium>() {
    override fun areItemsTheSame(oldItem: AnimeListQuery.Medium, newItem: AnimeListQuery.Medium): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimeListQuery.Medium, newItem: AnimeListQuery.Medium): Boolean {
        return oldItem == newItem
    }
}