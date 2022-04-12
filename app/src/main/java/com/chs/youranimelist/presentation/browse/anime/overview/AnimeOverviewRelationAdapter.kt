package com.chs.youranimelist.presentation.browse.anime.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemRelationBinding

class AnimeOverviewRelationAdapter(
    private val items: List<AnimeOverviewQuery.RelationsEdge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<AnimeOverviewRelationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRelationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    items[layoutPosition]!!.relationsNode!!.id,
                    items[layoutPosition]!!.relationsNode!!.idMal ?: 0
                )
            }
        }

        fun bind(relation: AnimeOverviewQuery.RelationsEdge?) {
            binding.model = relation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRelationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Log.e("onViewDetachedFromWindow", holder.layoutPosition.toString())
    }

}