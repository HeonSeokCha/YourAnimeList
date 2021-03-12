package com.chs.youranimelist.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.ui.home.HomeRecListChildDiffUtilCallBack

class AnimeListAdapter(
    private val list: List<AnimeList?>,
    private val clickListener: (animeId: Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    inner class AnimeListViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(list[layoutPosition]!!.id)
            }
        }

        fun bind() {
            binding.model = list[layoutPosition]
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return list.size
    }

}