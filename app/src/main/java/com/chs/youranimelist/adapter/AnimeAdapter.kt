package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding
import java.lang.Exception

class AnimeAdapter(
    private val clickListener: (animeId: Int,animeName: String) -> Unit,
): ListAdapter<Any, AnimeAdapter.AnimeViewHolder>(AnimeDiffUtilCallBack()) {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                castTypeClickListener(getItem(layoutPosition))
            }
        }
        fun bind() {
            binding.model = getItem(layoutPosition)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = currentList.size

    fun castTypeClickListener(any: Any) {
        when (any) {
            is AnimeRecListQuery.TrendingMedium -> {
                if(any.fragments.animeList.title?.english == null) {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                } else {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                }
            }
            is AnimeRecListQuery.PopularMedium -> {
                if(any.fragments.animeList.title?.english == null) {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                } else {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                }
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                if(any.fragments.animeList.title?.english == null) {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                } else {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                }
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                if(any.fragments.animeList.title?.english == null) {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                } else {
                    clickListener.invoke(any.fragments.animeList.id,
                        any.fragments.animeList.title?.romaji!!)
                }
            }
        }
    }
}