package com.chs.youranimelist.presentation.browse.anime.overview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeOverviewQuery
import com.chs.youranimelist.databinding.ItemLinkBinding
import com.chs.youranimelist.util.Constant
import java.util.*

class AnimeOverviewLinkAdapter(
    private val list: List<AnimeOverviewQuery.ExternalLink?>,
    private val clickListener: (url: String) -> Unit
) : RecyclerView.Adapter<AnimeOverviewLinkAdapter.AnimeOverviewLinkViewHolder>() {

    inner class AnimeOverviewLinkViewHolder(val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(list[layoutPosition]!!.url)
            }
        }

        fun bind(link: AnimeOverviewQuery.ExternalLink?) {
            binding.model = link
            if (Constant.EXTERNAL_LINK.containsKey(link!!.site.lowercase(Locale.getDefault()))) {
                binding.linkCard.setCardBackgroundColor(
                    Color.parseColor(
                        Constant.EXTERNAL_LINK[link.site.lowercase(Locale.getDefault())]
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeOverviewLinkViewHolder {
        val view = ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeOverviewLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeOverviewLinkViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}