package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.databinding.ItemViewPagerBinding

class ViewPagerAdapter (
    private val clickListener: (anime: AnimeRecListQuery.Medium) -> Unit,
    ): ListAdapter<AnimeRecListQuery.Medium,ViewPagerAdapter.ViewPagerViewHolder>(ViewPagerDiffUtilCallBack()) {


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


