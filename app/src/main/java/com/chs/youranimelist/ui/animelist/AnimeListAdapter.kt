package com.chs.youranimelist.ui.animelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.Anime
import com.chs.youranimelist.databinding.ItemAnimeListBinding

class AnimeListAdapter :
    ListAdapter<Anime, AnimeListAdapter.AnimeListViewHolder>(AnimeListComparator()) {

    inner class AnimeListViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.model = getItem(layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}