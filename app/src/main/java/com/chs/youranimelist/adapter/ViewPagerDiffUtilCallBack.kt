package com.chs.youranimelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.ViewPagerQuery

class ViewPagerDiffUtilCallBack: DiffUtil.ItemCallback<ViewPagerQuery.Medium>() {
    override fun areItemsTheSame(oldItem: ViewPagerQuery.Medium, newItem: ViewPagerQuery.Medium): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ViewPagerQuery.Medium, newItem: ViewPagerQuery.Medium): Boolean {
        return oldItem == newItem
    }
}