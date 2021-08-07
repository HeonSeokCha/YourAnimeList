package com.chs.youranimelist.ui.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeThemeBinding

class AnimeOverviewThemeAdapter(private val list: List<String>) :
    RecyclerView.Adapter<AnimeOverviewThemeAdapter.AnimeOverviewThemeViewHolder>() {

    class AnimeOverviewThemeViewHolder(val binding: ItemAnimeThemeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(themeName: String) {
            binding.txtAnimeTheme.text = themeName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeOverviewThemeViewHolder {
        val view = ItemAnimeThemeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeOverviewThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeOverviewThemeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}