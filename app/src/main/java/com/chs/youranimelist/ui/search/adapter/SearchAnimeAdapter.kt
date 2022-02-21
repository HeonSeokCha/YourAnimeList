package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchMediaBinding
import com.chs.youranimelist.data.remote.dto.SearchResult
import com.chs.youranimelist.search.SearchAnimeQuery

class SearchAnimeAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<SearchAnimeQuery.Medium, RecyclerView.ViewHolder>(diffUtil) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<SearchAnimeQuery.Medium>() {
            override fun areItemsTheSame(
                oldItem: SearchAnimeQuery.Medium,
                newItem: SearchAnimeQuery.Medium
            ): Boolean {
                return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
            }

            override fun areContentsTheSame(
                oldItem: SearchAnimeQuery.Medium,
                newItem: SearchAnimeQuery.Medium
            ): Boolean {
                return oldItem.fragments.animeList == newItem.fragments.animeList
            }
        }
    }

    inner class SearchAnimeViewHolder(val binding: ItemSearchMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition).fragments.animeList.id,
                    getItem(layoutPosition).fragments.animeList.idMal ?: 0
                )
            }
        }

        fun bind(item: SearchAnimeQuery.Medium) {
            binding.model = item.fragments.animeList
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemSearchMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SearchAnimeViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchAnimeViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}