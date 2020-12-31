package com.chs.youranimelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.network.dto.Anime

class ViewPagerDiffUtilCallBack: DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }
}