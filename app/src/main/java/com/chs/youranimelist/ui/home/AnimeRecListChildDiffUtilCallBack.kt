package com.chs.youranimelist.ui.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class AnimeRecListChildDiffUtilCallBack:DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}