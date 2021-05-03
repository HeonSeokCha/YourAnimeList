package com.chs.youranimelist.ui.sortedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.fragment.AnimeList

class SortedListAdapter(
    private val items: ArrayList<AnimeList?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    class SortedListViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SortedListViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SortedListViewHolder) {
            holder.binding.root.setOnClickListener {
                clickListener.invoke(items[position]!!.id, items[position]!!.idMal!!)
            }
            holder.binding.model = items[position]
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position]?.id?.toLong() ?: 9999L
}