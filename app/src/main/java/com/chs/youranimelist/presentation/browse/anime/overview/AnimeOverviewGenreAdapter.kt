package com.chs.youranimelist.presentation.browse.anime.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemGenreBinding

class AnimeOverviewGenreAdapter(
    private val items: List<String?>,
    private val clickListener: (genre: String) -> Unit
) : RecyclerView.Adapter<AnimeOverviewGenreAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(items[layoutPosition]!!)
            }
        }

        fun bind(genreName: String?) {
            binding.model = genreName.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}