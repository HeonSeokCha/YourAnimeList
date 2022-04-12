package com.chs.youranimelist.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.fragment.AnimeList

class HomeRecListChildAdapter(
    private val list: List<AnimeList>,
    private val clickListener: (id: Int, idMal: Int) -> Unit,
) : RecyclerView.Adapter<HomeRecListChildAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(list[position].id, list[position].idMal ?: 0)
            }
        }

        fun bind(anime: AnimeList) {
            binding.model = anime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}