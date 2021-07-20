package com.chs.youranimelist.ui.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemStudioBinding

class AnimeOverviewStudioAdapter(
    private val items: List<AnimeOverviewQuery.StudiosNode>,
    private val clickListener: (studioId: Int) -> Unit
) :
    RecyclerView.Adapter<AnimeOverviewStudioAdapter.AnimeOverviewStudioViewHolder>() {

    class AnimeOverviewStudioViewHolder(val binding: ItemStudioBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeOverviewStudioViewHolder {
        val view = ItemStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = AnimeOverviewStudioViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            clickListener.invoke(items[viewHolder.layoutPosition].id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AnimeOverviewStudioViewHolder, position: Int) {
        holder.binding.model = items[position].name
    }

    override fun getItemCount(): Int = items.size
}