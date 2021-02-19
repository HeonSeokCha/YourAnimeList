package com.chs.youranimelist.ui.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemRelationBinding

class AnimeOverviewRelationAdapter(
    private val items: List<AnimeOverviewQuery.RelationsEdge?>,
    private val clickListener: (type: String, id: Int) -> Unit
) : RecyclerView.Adapter<AnimeOverviewRelationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRelationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    items[layoutPosition]!!.relationType!!.name,
                    items[layoutPosition]!!.relationsNode!!.id
                )
            }
        }

        fun bind() {
            binding.model = items[layoutPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRelationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

}