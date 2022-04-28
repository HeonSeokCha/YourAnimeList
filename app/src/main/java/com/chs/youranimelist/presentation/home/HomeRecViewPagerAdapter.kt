package com.chs.youranimelist.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemViewPagerBinding
import com.chs.youranimelist.home.HomeRecommendListQuery

class HomeRecViewPagerAdapter(
    private val items: List<HomeRecommendListQuery.Medium?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<HomeRecViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    items[layoutPosition]!!.id,
                    items[layoutPosition]!!.idMal ?: 0
                )
            }
        }

        fun bind(homeItem: HomeRecommendListQuery.Medium?) {
            binding.model = homeItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

