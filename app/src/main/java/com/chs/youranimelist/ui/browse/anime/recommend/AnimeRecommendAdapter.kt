package com.chs.youranimelist.ui.browse.anime.recommend

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding

class AnimeRecommendAdapter(
    private val items: List<AnimeRecommendQuery.Node>
) : RecyclerView.Adapter<AnimeRecommendAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size
}