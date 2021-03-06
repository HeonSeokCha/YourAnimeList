package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.databinding.ItemViewPagerBinding

class HomeRecViewPagerAdapter(
    private val items: List<HomeRecommendListQuery.Medium?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<HomeRecViewPagerAdapter.ViewPagerViewHolder>() {

    class ViewPagerViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            clickListener.invoke(items[position]!!.id, items[position]!!.idMal!!)
        }
        holder.binding.model = items[position]
    }

    override fun getItemCount(): Int = items.size
}


