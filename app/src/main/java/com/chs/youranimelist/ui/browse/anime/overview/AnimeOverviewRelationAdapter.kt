package com.chs.youranimelist.ui.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemRelationBinding

class AnimeOverviewRelationAdapter(
    private val items: List<AnimeOverviewQuery.RelationsEdge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<AnimeOverviewRelationAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRelationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRelationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = items[position]
        holder.binding.root.setOnClickListener {
            clickListener.invoke(
                items[position]!!.relationsNode!!.id,
                items[position]!!.relationsNode!!.idMal!!
            )
        }
    }

    override fun getItemCount(): Int = items.size

}