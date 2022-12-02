package com.chs.youranimelist.presentation.browse.anime.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.databinding.ItemCharacterBinding

class AnimeCharaAdapter(
    private val items: List<AnimeDetailQuery.CharactersNode?>,
    private val clickListener: (charaId: Int) -> Unit
) : RecyclerView.Adapter<AnimeCharaAdapter.CharaViewHolder>() {

    class CharaViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharaViewHolder {
        val view = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharaViewHolder, position: Int) {
        holder.binding.model = items[position]
        holder.binding.root.setOnClickListener {
            clickListener.invoke(items[position]!!.id)
        }
    }

    override fun getItemCount(): Int = items.size
}