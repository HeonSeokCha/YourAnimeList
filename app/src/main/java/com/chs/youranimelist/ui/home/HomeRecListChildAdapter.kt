package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.type.MediaStatus

class HomeRecListChildAdapter(
    private val list: List<AnimeList>,
    private val clickListener: (id: Int, idMal: Int) -> Unit,
) : RecyclerView.Adapter<HomeRecListChildAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            clickListener.invoke(list[position].id, list[position].idMal!!)
        }
        holder.binding.model = list[position]
    }

    override fun getItemCount(): Int = list.size
}