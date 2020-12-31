package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemViewPagerBinding
import com.chs.youranimelist.network.dto.Anime

class ViewPagerAdapter (
//    private val clickListener: (anime: Anime, position: Int) -> Unit,
    ):ListAdapter<Anime, ViewPagerAdapter.ViewPagerViewHolder> (ViewPagerDiffUtilCallBack()) {

    inner class ViewPagerViewHolder(
        private val binding: ItemViewPagerBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind() {
                binding.model = getItem(bindingAdapterPosition)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        var binding = ItemViewPagerBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}
