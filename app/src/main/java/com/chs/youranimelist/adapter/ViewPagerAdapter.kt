package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.chs.youranimelist.databinding.ItemViewPagerBinding
import com.chs.youranimelist.network.dto.Anime

class ViewPagerAdapter (
    private val clickListener: (anime: Anime) -> Unit,
    ): ListAdapter<Anime,ViewPagerAdapter.ViewPagerViewHolder>(ViewPagerDiffUtilCallBack()) {


    inner class ViewPagerViewHolder(private val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(bindingAdapterPosition))
            }
        }
            fun bind() {
                binding.model = getItem(bindingAdapterPosition)
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind()
    }
}


