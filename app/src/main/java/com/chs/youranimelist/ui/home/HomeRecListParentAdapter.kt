package com.chs.youranimelist.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeListBinding
import com.chs.youranimelist.databinding.ItemAnimeParentBinding
import com.chs.youranimelist.fragment.AnimeList

class HomeRecListParentAdapter(
    private val list: List<List<AnimeList>>,
    private val mContext: Context,
    private val listener: HomeRecListener
) : RecyclerView.Adapter<HomeRecListParentAdapter.AnimeListRecViewHolder>() {

    interface HomeRecListener {
        fun clickMore(sortType: String)
        fun clickAnime(id: Int, idMal: Int)
    }

    lateinit var homeAdapter: HomeRecListChildAdapter
    private val listTitleList = listOf(
        "TRENDING NOW", "POPULAR THIS SEASON",
        "UPCOMING NEXT SEASON", "ALL TIME POPULAR",
    )


    inner class AnimeListRecViewHolder(val binding: ItemAnimeParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnViewAll.setOnClickListener {
                listener.clickMore(listTitleList[layoutPosition])
            }
        }

        fun bind(title: String, itemList: List<AnimeList>) {
            binding.model = title
            binding.rvAnime.apply {
                homeAdapter = HomeRecListChildAdapter(itemList) { id, idMal ->
                    listener.clickAnime(id, idMal)
                }
                this.adapter = homeAdapter
                this.layoutManager = LinearLayoutManager(
                    mContext,
                    LinearLayoutManager.HORIZONTAL, false
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListRecViewHolder {
        val view =
            ItemAnimeParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListRecViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListRecViewHolder, position: Int) {
        holder.bind(listTitleList[position], list[position])
    }

    override fun getItemCount(): Int = list.size
}