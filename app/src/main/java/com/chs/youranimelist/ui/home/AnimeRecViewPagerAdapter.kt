package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.databinding.ItemViewPagerBinding

class AnimeRecViewPagerAdapter (
    private val items: List<AnimeRecListQuery.Medium?>,
    private val clickListener: (animeId: Int,animeName: String) -> Unit,
    private val trailerClickListener: (animeId: String) -> Unit
    ): RecyclerView.Adapter<AnimeRecViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                with(items[layoutPosition]!!) {
                    if(this.title?.english == null) {
                        clickListener.invoke(this.id, this.title?.romaji!!)
                    } else {
                        clickListener.invoke(this.id, this.title?.english!!)
                    }
                }
            }
            binding.floatingActionButton.setOnClickListener {
                trailerClickListener.invoke(items[layoutPosition]?.trailer?.id!!)
            }
        }
        fun bind() {
            binding.model = items[layoutPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size
}


