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
                castTypeClickListener((items as List<*>)[layoutPosition]!!)
            }
        }
        fun bind() {
            binding.model = (items as List<*>)[layoutPosition]
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = (items as List<*>).size

    fun castTypeClickListener(any: Any) {
        when (any) {
            is AnimeRecListQuery.TrendingMedium -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.PopularMedium -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                clickListener.invoke(any.id)
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                clickListener.invoke(any.id)
            }
        }
    }
}