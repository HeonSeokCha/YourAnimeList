package com.chs.youranimelist.ui.browse.anime.overview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.util.ItemColor
import com.chs.youranimelist.databinding.ItemLinkBinding
import java.util.*

class AnimeOverviewLinkAdapter(
    private val list: List<AnimeOverviewQuery.ExternalLink?>,
    private val clickListener: (url: String) -> Unit
) : RecyclerView.Adapter<AnimeOverviewLinkAdapter.AnimeOverviewLinkViewHolder>() {

    class AnimeOverviewLinkViewHolder(val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeOverviewLinkViewHolder {
        val view = ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeOverviewLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeOverviewLinkViewHolder, position: Int) {
        holder.binding.model = list[position]
        if (ItemColor.EXTERNAL_LINK.containsKey(list[position]!!.site.lowercase(Locale.getDefault()))) {
            holder.binding.linkCard.setCardBackgroundColor(
                Color.parseColor(ItemColor.EXTERNAL_LINK[list[position]!!.site.lowercase(Locale.getDefault())])
            )
        }
        holder.binding.root.setOnClickListener {
            clickListener.invoke(list[position]!!.url)
        }
    }

    override fun getItemCount(): Int = list.size
}