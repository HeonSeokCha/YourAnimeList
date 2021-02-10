package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.fragment.AnimeList

class AnimeRecListChildAdapter(
    private val list: List<AnimeList>,
    private val clickListener: (animeId: Int) -> Unit,
): RecyclerView.Adapter<AnimeRecListChildAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(list[layoutPosition].id)
            }
        }
        fun bind() {
            binding.model = list[layoutPosition]
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = list.size

//    fun castTypeClickListener(any: Any) {
//        when (any) {
//            is AnimeRecListQuery.TrendingMedium -> {
//                if(any.fragments.animeList.title?.english == null) {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                } else {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                }
//            }
//            is AnimeRecListQuery.PopularMedium -> {
//                if(any.fragments.animeList.title?.english == null) {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                } else {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                }
//            }
//            is AnimeRecListQuery.UpcommingMedium -> {
//                if(any.fragments.animeList.title?.english == null) {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                } else {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                }
//            }
//            is AnimeRecListQuery.AlltimeMedium -> {
//                if(any.fragments.animeList.title?.english == null) {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                } else {
//                    clickListener.invoke(any.fragments.animeList.id,
//                        any.fragments.animeList.title?.romaji!!)
//                }
//            }
//        }
//    }
}