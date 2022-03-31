package com.chs.youranimelist.ui.browse.anime.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.databinding.ItemCharacterBinding

class AnimeCharaAdapter(
    private val items: List<AnimeCharacterQuery.CharactersNode?>,
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