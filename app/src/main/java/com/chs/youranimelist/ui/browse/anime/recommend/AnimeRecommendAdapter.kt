package com.chs.youranimelist.ui.browse.anime.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding
import com.chs.youranimelist.databinding.ItemCharacterRecommendBinding

class AnimeRecommendAdapter(
    private val items: List<AnimeRecommendQuery.Edge?>,
    private val clickListener: (id: Int) -> Unit
) : RecyclerView.Adapter<AnimeRecommendAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCharacterRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(items[layoutPosition]!!.node!!.mediaRecommendation!!.id)
            }
        }

        fun bind() {
            binding.model = items[layoutPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCharacterRecommendBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size
}