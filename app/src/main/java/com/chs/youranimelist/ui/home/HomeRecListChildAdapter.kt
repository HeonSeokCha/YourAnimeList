package com.chs.youranimelist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.fragment.AnimeList

class HomeRecListChildAdapter(
    private val list: List<AnimeList>,
    private val clickListener: (animeId: Int) -> Unit,
) : RecyclerView.Adapter<HomeRecListChildAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
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
}