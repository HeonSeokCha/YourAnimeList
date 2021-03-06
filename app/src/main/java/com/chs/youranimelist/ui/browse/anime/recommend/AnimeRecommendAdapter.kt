package com.chs.youranimelist.ui.browse.anime.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.databinding.ItemAnimeRecommendBinding

class AnimeRecommendAdapter(
    private val items: List<AnimeRecommendQuery.Edge?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<AnimeRecommendAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemAnimeRecommendBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAnimeRecommendBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = items[position]!!.node
        holder.binding.root.setOnClickListener {
            clickListener.invoke(
                items[position]!!.node!!.mediaRecommendation!!.id,
                items[position]!!.node!!.mediaRecommendation!!.idMal!!
            )
        }
    }

    override fun getItemCount(): Int = items.size
}