package com.chs.youranimelist.ui.browse.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemRelationBinding
import com.chs.youranimelist.fragment.AnimeList

class StudioAnimeAdapter(
    private val items: List<StudioAnimeQuery.Edge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<StudioAnimeAdapter.StudioAnimeViewHolder>() {

    class StudioAnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioAnimeViewHolder {
        val view = ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudioAnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudioAnimeViewHolder, position: Int) {
        holder.binding.model = items[position]!!.node!!.fragments.animeList
        holder.binding.root.setOnClickListener {
            clickListener.invoke(
                items[position]!!.node!!.fragments.animeList.id,
                items[position]!!.node!!.fragments.animeList.idMal ?: 0
            )
        }
    }

    override fun getItemCount(): Int = items.size

}