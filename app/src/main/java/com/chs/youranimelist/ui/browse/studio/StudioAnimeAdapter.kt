package com.chs.youranimelist.ui.browse.studio

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.studio.StudioAnimeQuery
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding

class StudioAnimeAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<StudioAnimeQuery.Edge?, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<StudioAnimeQuery.Edge?>() {
            override fun areItemsTheSame(
                oldItem: StudioAnimeQuery.Edge,
                newItem: StudioAnimeQuery.Edge
            ): Boolean {
                return oldItem.node!!.fragments.animeList.id == newItem.node!!.fragments.animeList.id
            }

            override fun areContentsTheSame(
                oldItem: StudioAnimeQuery.Edge,
                newItem: StudioAnimeQuery.Edge
            ): Boolean {
                return oldItem.node!!.fragments.animeList == newItem.node!!.fragments.animeList
            }
        }
    }

    inner class StudioAnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition)!!.node!!.fragments.animeList.id,
                    getItem(layoutPosition)!!.node!!.fragments.animeList.idMal ?: 0
                )
            }
        }

        fun bind(anime: StudioAnimeQuery.Edge?) {
            binding.model = anime!!.node!!.fragments.animeList
        }
    }

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
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}