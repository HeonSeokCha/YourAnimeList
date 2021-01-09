package com.chs.youranimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeListQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding
import java.lang.Exception

class AnimeAdapter(
    private val items: Any,
    private val clickListener: (animeId: Int) -> Unit,
): RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        private val binding: ItemAnimeBinding
    ):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                castTypeClickListener((items as List<AnimeListQuery.Medium>)[layoutPosition])
            }
        }
        fun bind() {
            binding.model = (items as List<AnimeListQuery.Medium>)[layoutPosition]
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 6

    fun castTypeClickListener(any: Any) {
        when (any) {
            is AnimeRecListQuery.Medium1 -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.Medium2 -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.Medium3 -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.Medium4 -> {
                clickListener.invoke(any.id)
            }
        }
    }
}