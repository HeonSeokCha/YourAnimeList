package com.chs.youranimelist.ui.sortedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.ui.home.HomeRecListChildDiffUtilCallBack

class SortedListAdapter(
    private val clickListener: (animeId: Int) -> Unit,
) : ListAdapter<AnimeList, RecyclerView.ViewHolder>(HomeRecListChildDiffUtilCallBack()) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    inner class AnimeListViewHolder(
        private val binding: ItemAnimeChildBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).id)
            }
        }

        fun bind() {
            binding.model = getItem(layoutPosition)
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AnimeListViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnimeListViewHolder) {
            holder.bind()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}