package com.chs.youranimelist.ui.animelist

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.data.Anime

class AnimeListComparator : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }
}