package com.chs.youranimelist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.ViewPagerQuery
import com.chs.youranimelist.databinding.ItemAnimeListBinding
import com.chs.youranimelist.databinding.ItemViewPagerBinding

class AnimeListAdapter(
    private val mcontext: Context,
    private val clickListener: (animeId: Int) -> Unit,
    private val animeclickListener: (animeId: Int) -> Unit,
):ListAdapter<Any,AnimeListAdapter.AnimeListViewHolder>(AnimeListDiffUtilCallBack()) {
    private lateinit var animeAdapter:AnimeAdapter
    private var listTitleList = listOf<String> (
        "TRENDING NOW","POPULAR THIS SEASON","UPCOMING NEXT SEASON","ALL TIME POPULAR")
    inner class AnimeListViewHolder(private val binding: ItemAnimeListBinding)
        :RecyclerView.ViewHolder(binding.root) {
            fun bind() {
                binding.model = listTitleList[layoutPosition]
                binding.rvAnime.apply {
                    animeAdapter = AnimeAdapter(getItem(layoutPosition),clickListener = {
                        animeclickListener.invoke(it)
                    })
                    this.adapter = animeAdapter
                    this.layoutManager = LinearLayoutManager(mcontext,
                        LinearLayoutManager.HORIZONTAL,false)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnimeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind()
    }
}