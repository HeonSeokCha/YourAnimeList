package com.chs.youranimelist.presentation.sortedlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.fragment.AnimeList

class SortedListAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit,
) : ListAdapter<AnimeList?, RecyclerView.ViewHolder>(diffUtil) {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<AnimeList?>() {
            override fun areItemsTheSame(
                oldItem: AnimeList,
                newItem: AnimeList
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AnimeList,
                newItem: AnimeList
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SortedListViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition)!!.id,
                    getItem(layoutPosition)!!.idMal ?: 0
                )
            }
        }

        fun bind(itemAnime: AnimeList?) {
            binding.model = itemAnime
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    var holderSize: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            Log.e("viewHolder", "onCreateViewHolder ${holderSize++}")
            SortedListViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SortedListViewHolder) {
            holder.bind(getItem(position))
        }
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is SortedListViewHolder) {
            holder.binding.imgAnimeList.dispose()
        }
        Log.e("onViewRecycled", "${holder.layoutPosition}")
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is SortedListViewHolder) {
            holder.binding.imgAnimeList.dispose()
        }
        Log.e("onViewDetachedFromWindow", "${holder.layoutPosition}")
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}