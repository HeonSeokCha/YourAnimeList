package com.chs.youranimelist.presentation.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemStudioBinding

class AnimeOverviewStudioAdapter(
    private val items: List<AnimeOverviewQuery.StudiosNode>,
    private val clickListener: (studioId: Int) -> Unit
) :
    RecyclerView.Adapter<AnimeOverviewStudioAdapter.AnimeOverviewStudioViewHolder>() {

    inner class AnimeOverviewStudioViewHolder(val binding: ItemStudioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(items[layoutPosition].id)
            }
        }

        fun bind(studio: AnimeOverviewQuery.StudiosNode) {
            binding.model = studio.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeOverviewStudioViewHolder {
        val view = ItemStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeOverviewStudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeOverviewStudioViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}