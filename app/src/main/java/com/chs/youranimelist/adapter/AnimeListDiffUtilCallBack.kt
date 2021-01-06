package com.chs.youranimelist.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class AnimeListDiffUtilCallBack: DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}