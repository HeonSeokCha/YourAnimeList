package com.chs.youranimelist.ui.animelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.Anime
import com.chs.youranimelist.databinding.ItemAnimeListBinding
import com.chs.youranimelist.ui.browse.anime.overview.AnimeOverviewGenreAdapter

class AnimeListAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<Anime, AnimeListAdapter.AnimeListViewHolder>(AnimeListComparator()) {

    class AnimeListViewHolder(val binding: ItemAnimeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.binding.model = getItem(position)
        holder.binding.root.setOnClickListener {
            clickListener.invoke(getItem(position).animeId)
        }
        if (!getItem(position).genre.isNullOrEmpty()) {
            holder.binding.rvAnimeListGenre.apply {
                isVisible = true
                holder.binding.rvAnimeListGenre.adapter =
                    AnimeOverviewGenreAdapter(getItem(position).genre!!)
            }
        } else holder.binding.rvAnimeListGenre.isVisible = false
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}