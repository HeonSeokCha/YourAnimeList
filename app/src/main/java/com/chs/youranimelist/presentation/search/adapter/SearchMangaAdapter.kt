package com.chs.youranimelist.presentation.search.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchMediaBinding
import com.chs.youranimelist.search.SearchMangaQuery

class SearchMangaAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<SearchMangaQuery.Medium, RecyclerView.ViewHolder>(diffUtil) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<SearchMangaQuery.Medium>() {
            override fun areItemsTheSame(
                oldItem: SearchMangaQuery.Medium,
                newItem: SearchMangaQuery.Medium
            ): Boolean {
                return oldItem.fragments.animeList.id == newItem.fragments.animeList.id
            }

            override fun areContentsTheSame(
                oldItem: SearchMangaQuery.Medium,
                newItem: SearchMangaQuery.Medium
            ): Boolean {
                return oldItem.fragments.animeList == newItem.fragments.animeList
            }
        }
    }

    inner class SearchMangaViewHolder(val binding: ItemSearchMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition).fragments.animeList.id,
                    getItem(layoutPosition).fragments.animeList.idMal ?: 0
                )
            }
        }

        fun bind(items: SearchMangaQuery.Medium) {
            binding.model = items.fragments.animeList
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemSearchMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SearchMangaViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchMangaViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        Log.e("onViewRecycled", holder.layoutPosition.toString())
    }
}
