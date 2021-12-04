package com.chs.youranimelist.ui.browse.anime.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.databinding.ItemAnimeRecommendBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding

class AnimeRecommendAdapter(
    private val items: List<AnimeRecommendQuery.Edge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    inner class ViewHolder(val binding: ItemAnimeRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    items[layoutPosition]!!.node!!.mediaRecommendation!!.id,
                    items[layoutPosition]!!.node!!.mediaRecommendation!!.idMal ?: 0
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
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}