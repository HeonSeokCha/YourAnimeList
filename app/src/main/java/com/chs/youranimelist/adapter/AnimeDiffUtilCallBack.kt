package com.chs.youranimelist.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.AnimeListQuery

class AnimeDiffUtilCallBack:DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}