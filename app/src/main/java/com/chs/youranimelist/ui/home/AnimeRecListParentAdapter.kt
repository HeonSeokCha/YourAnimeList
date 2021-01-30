package com.chs.youranimelist.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeListBinding

class AnimeRecListParentAdapter(
    private val mContext: Context,
    private val clickListener: (sortType: String) -> Unit,
    private val animeClickListener: (animeId: Int,animeName: String) -> Unit,
):ListAdapter<Any, AnimeRecListParentAdapter.AnimeListRecViewHolder>(AnimeRecListParentDiffUtilCallBack()) {
    lateinit var animeAdapter: AnimeRecListChildAdapter
    private var listTitleList = listOf ("TRENDING NOW","POPULAR THIS SEASON",
        "UPCOMING NEXT SEASON","ALL TIME POPULAR")
    inner class AnimeListRecViewHolder(private val binding: ItemAnimeListBinding)
        :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnViewAll.setOnClickListener {
                clickListener.invoke(listTitleList[layoutPosition])
            }
        }
            fun bind() {
                binding.model = listTitleList[layoutPosition]
                binding.rvAnime.apply {
                    animeAdapter = AnimeRecListChildAdapter(clickListener = { animeId, animeName->
                        animeClickListener.invoke(animeId,animeName)
                    }).apply {
                        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    }
                    animeAdapter.submitList(currentList[layoutPosition] as List<*>)
                    this.adapter = animeAdapter
                    this.layoutManager = LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL,false)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListRecViewHolder {
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnimeListRecViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListRecViewHolder, position: Int) {
        holder.bind()
    }
}