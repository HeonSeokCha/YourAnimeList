package com.chs.youranimelist.ui.browse.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.StudioAnimeQuery
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemRelationBinding
import com.chs.youranimelist.fragment.AnimeList

class StudioAnimeAdapter(
    private val items: List<StudioAnimeQuery.Edge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    class StudioAnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StudioAnimeViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StudioAnimeViewHolder) {
            holder.binding.model = items[position]!!.node!!.fragments.animeList
            holder.binding.root.setOnClickListener {
                clickListener.invoke(
                    items[position]!!.node!!.fragments.animeList.id,
                    items[position]!!.node!!.fragments.animeList.idMal ?: 0
                )
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}