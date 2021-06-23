package com.chs.youranimelist.ui.browse.anime.overview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemGenreBinding
import com.chs.youranimelist.util.Constant

class AnimeOverviewGenreAdapter(
    private val items: List<String?>,
    private val clickListener: (genre: String) -> Unit
) : RecyclerView.Adapter<AnimeOverviewGenreAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = items[position]
        holder.binding.root.setOnClickListener {
            clickListener.invoke(items[position]!!)
        }
        holder.binding.genreCard
            .setCardBackgroundColor(Color.parseColor(Constant.GENRE_COLOR[items[position]]))
    }

    override fun getItemCount(): Int = items.size
}