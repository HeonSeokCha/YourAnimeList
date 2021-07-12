package com.chs.youranimelist.ui.browse.studio

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.databinding.ItemRelationBinding

class StudioAnimeAdapter(
    private val item: List<StudioAnimeQuery.Node>
) : RecyclerView.Adapter<StudioAnimeAdapter.StudioAnimeViewHolder>() {

    class StudioAnimeViewHolder(private val binding: ItemRelationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioAnimeViewHolder {
        TODO("Not Relation only Studio Anime layout.")
    }

    override fun onBindViewHolder(holder: StudioAnimeViewHolder, position: Int) {
        TODO("Not Relation only Studio Anime layout.")
    }

    override fun getItemCount(): Int = item.size

}