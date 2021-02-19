package com.chs.youranimelist.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.ui.home.AnimeRecListChildDiffUtilCallBack

class AnimeListAdapter(
    private val clickListener: (animeId: Int) -> Unit,
) : ListAdapter<AnimeList, AnimeListAdapter.AnimeListViewHolder>(AnimeRecListChildDiffUtilCallBack()) {
    inner class AnimeListViewHolder(
        private val binding: ItemAnimeBinding
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind()
    }

}