package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.fragment.AnimeList

class AnimeListAdapter(
    private val clickListener: (animeId: Int,animeName: String) -> Unit,
): ListAdapter<AnimeList, AnimeListAdapter.AnimeListViewHolder>(AnimeRecListChildDiffUtilCallBack()) {
    inner class AnimeListViewHolder(
        private val binding: ItemAnimeBinding):RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    if(getItem(layoutPosition).title?.english == null) {
                        clickListener.invoke(getItem(layoutPosition).id,getItem(layoutPosition).title?.romaji!!)
                    } else {
                        clickListener.invoke(getItem(layoutPosition).id,getItem(layoutPosition).title?.english!!)
                    }
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

}