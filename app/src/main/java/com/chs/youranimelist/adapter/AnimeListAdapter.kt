package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding

class AnimeListAdapter(
    private val clickListener: (animeId: Int) -> Unit,
): ListAdapter<Any,AnimeListAdapter.AnimeListViewHolder>(AnimeDiffUtilCallBack()) {
    inner class AnimeListViewHolder(
        private val binding: ItemAnimeBinding):RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    castTypeClickListener(getItem(layoutPosition))
                }
            }
            fun bind() {
                binding.model = getItem(layoutPosition)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind()
    }

    fun castTypeClickListener(any: Any) {
        when (any) {
            is AnimeListQuery.Medium -> {
                clickListener.invoke(any.id)
            }
            is AnimeListQuery.Medium1 -> {
                clickListener.invoke(any.id)
            }
        }
    }
}