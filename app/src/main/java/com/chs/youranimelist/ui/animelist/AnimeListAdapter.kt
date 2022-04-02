package com.chs.youranimelist.ui.animelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.databinding.ItemAnimeListBinding
import com.chs.youranimelist.ui.browse.anime.characters.AnimeCharaAdapter
import com.chs.youranimelist.ui.browse.anime.overview.AnimeOverviewGenreAdapter

class AnimeListAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<Anime, AnimeListAdapter.AnimeListViewHolder>(AnimeListComparator()) {

    inner class AnimeListViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition).animeId,
                    getItem(layoutPosition).idMal
                )
            }
        }

        fun bind() {
            binding.model = getItem(layoutPosition)

            if (!getItem(layoutPosition).genre.isNullOrEmpty()) {
                binding.rvAnimeListGenre.apply {
                    isVisible = true
                    this.setRecycledViewPool(recycledViewPool)
                    this.adapter = AnimeOverviewGenreAdapter(getItem(layoutPosition).genre!!) {}
                }
            } else binding.rvAnimeListGenre.isVisible = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind()
    }
}