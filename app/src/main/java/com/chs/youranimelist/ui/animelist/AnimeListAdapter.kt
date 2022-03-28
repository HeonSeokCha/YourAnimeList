package com.chs.youranimelist.ui.animelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.domain.model.Anime
import com.chs.youranimelist.databinding.ItemAnimeListBinding
import com.chs.youranimelist.ui.browse.anime.overview.AnimeOverviewGenreAdapter
import com.chs.youranimelist.ui.sortedlist.SortedListAdapter

class AnimeListAdapter(
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : ListAdapter<Anime, AnimeListAdapter.AnimeListViewHolder>(AnimeListComparator()) {

    inner class AnimeListViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.model = getItem(layoutPosition)
            binding.root.setOnClickListener {
                clickListener.invoke(
                    getItem(layoutPosition).animeId,
                    getItem(layoutPosition).idMal
                )
            }
            if (!getItem(layoutPosition).genre.isNullOrEmpty()) {
                binding.rvAnimeListGenre.apply {
                    isVisible = true
                    binding.rvAnimeListGenre.adapter =
                        AnimeOverviewGenreAdapter(getItem(layoutPosition).genre!!) {

                        }
                }
            } else binding.rvAnimeListGenre.isVisible = false
        }
    }
    var holderSize: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        Log.e("viewHolder", "onCreateViewHolder ${holderSize++}")
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        Log.e("viewHolder", "onBindViewHolder $position")
        holder.bind()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.e("onDetachedFromRecyclerView", "onDetachedFromRecyclerView")
    }

    override fun onFailedToRecycleView(holder: AnimeListAdapter.AnimeListViewHolder): Boolean {
        return super.onFailedToRecycleView(holder)
        Log.e("onFailedToRecycleView", "${holder.layoutPosition}")
    }

    override fun onViewAttachedToWindow(holder: AnimeListAdapter.AnimeListViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.e("onViewAttachedToWindow", "${holder.layoutPosition}")
    }

    override fun onViewRecycled(holder: AnimeListAdapter.AnimeListViewHolder) {
        super.onViewRecycled(holder)
        Log.e("onViewRecycled", "${holder.layoutPosition}")
    }

    override fun onViewDetachedFromWindow(holder: AnimeListAdapter.AnimeListViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.e("onViewDetachedFromWindow", "${holder.layoutPosition}")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.e("onAttachedToRecyclerView", "onAttachedToRecyclerView")
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}