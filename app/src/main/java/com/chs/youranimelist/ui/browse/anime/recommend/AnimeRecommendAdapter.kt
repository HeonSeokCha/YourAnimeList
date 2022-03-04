package com.chs.youranimelist.ui.browse.anime.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.databinding.ItemAnimeRecommendBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding

class AnimeRecommendAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<AnimeRecommendQuery.Edge,RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<AnimeRecommendQuery.Edge>() {
            override fun areItemsTheSame(
                oldItem: AnimeRecommendQuery.Edge,
                newItem: AnimeRecommendQuery.Edge
            ): Boolean {
                return oldItem.node!!.id == newItem.node!!.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeRecommendQuery.Edge,
                newItem: AnimeRecommendQuery.Edge
            ): Boolean {
                return oldItem.node == oldItem.node
            }
        }
    }

    inner class ViewHolder(val binding: ItemAnimeRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition)!!.node!!.mediaRecommendation!!.id,
                    getItem(layoutPosition)!!.node!!.mediaRecommendation!!.idMal ?: 0
                )
            }
        }

        fun bind(anime: AnimeRecommendQuery.Edge?) {
            binding.model = anime!!.node
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = ItemAnimeRecommendBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}