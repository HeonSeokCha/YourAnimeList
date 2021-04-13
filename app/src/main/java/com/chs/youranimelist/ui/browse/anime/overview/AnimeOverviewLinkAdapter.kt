package com.chs.youranimelist.ui.browse.anime.overview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.ItemColor
import com.chs.youranimelist.databinding.ItemLinkBinding

class AnimeOverviewLinkAdapter(
    private val list: List<AnimeOverviewQuery.ExternalLink?>
) : RecyclerView.Adapter<AnimeOverviewLinkAdapter.AnimeOverviewLinkViewHolder>() {

    inner class AnimeOverviewLinkViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.model = list[layoutPosition]
            binding.linkCard.setCardBackgroundColor(
                Color.parseColor(ItemColor.EXTERNAL_LINK[list[layoutPosition]?.site?.toLowerCase()])
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeOverviewLinkViewHolder {
        val view = ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeOverviewLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeOverviewLinkViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = list.size
}