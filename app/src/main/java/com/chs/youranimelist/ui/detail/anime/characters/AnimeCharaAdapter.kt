package com.chs.youranimelist.ui.detail.anime.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.databinding.ItemCharaBinding

class AnimeCharaAdapter(
    private val items:List<AnimeDetailQuery.CharactersNode?>
    ): RecyclerView.Adapter<AnimeCharaAdapter.CharaViewHolder>(){

    inner class CharaViewHolder(
        private val binding: ItemCharaBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
            }
        }
        fun bind() {
            binding.model = items[layoutPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharaViewHolder {
        val view = ItemCharaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharaViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size
}