package com.chs.youranimelist.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeListBinding
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

    inner class AnimeListRecViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnViewAll.setOnClickListener {
                clickListener.invoke(listTitleList[layoutPosition])
            }
        }

        fun bind() {
            binding.model = listTitleList[layoutPosition]
            binding.rvAnime.apply {
                homeAdapter =
                    HomeRecListChildAdapter(list[layoutPosition], clickListener = { animeId ->
                        animeClickListener.invoke(animeId)
                    }).apply {
                        this.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
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
        val view = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListRecViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeListRecViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 4
}