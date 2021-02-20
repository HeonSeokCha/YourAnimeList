package com.chs.youranimelist.ui.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.fragment.AnimeList

class HomeRecListChildDiffUtilCallBack : DiffUtil.ItemCallback<AnimeList>() {
    override fun areItemsTheSame(oldItem: AnimeList, newItem: AnimeList): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: AnimeList, newItem: AnimeList): Boolean {
        return oldItem == newItem
    }
}