package com.chs.youranimelist.ui.home

import android.content.Context
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
    private val clickListener: (sortType: String) -> Unit,
    private val animeClickListener: (animeId: Int) -> Unit,
) : RecyclerView.Adapter<HomeRecListParentAdapter.AnimeListRecViewHolder>() {
    lateinit var homeAdapter: HomeRecListChildAdapter
    private var listTitleList = listOf(
        "TRENDING NOW", "POPULAR THIS SEASON",
        "UPCOMING NEXT SEASON", "ALL TIME POPULAR"
    )

    class AnimeListRecViewHolder(val binding: ItemAnimeParentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListRecViewHolder {
        val view =
            ItemAnimeParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListRecViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListRecViewHolder, position: Int) {
        holder.binding.btnViewAll.setOnClickListener {
            clickListener.invoke(listTitleList[position])
        }
        holder.binding.model = listTitleList[position]
        holder.binding.rvAnime.apply {
            homeAdapter =
                HomeRecListChildAdapter(list[position], clickListener = { animeId ->
                    animeClickListener.invoke(animeId)
                }, mContext).apply {
                    this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            this.adapter = homeAdapter
            this.layoutManager = LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    override fun getItemCount(): Int = list.size
}