package com.chs.youranimelist.ui.browse.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.databinding.ItemAnimeChildBinding

class CharacterAnimeAdapter(
    private val items: List<CharacterQuery.Edge?>,
    private val clickListener: (id: Int) -> Unit
) :
    RecyclerView.Adapter<CharacterAnimeAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemAnimeChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = items[position]!!.node!!.fragments.animeList
        holder.binding.root.setOnClickListener {
            clickListener.invoke(items[position]!!.node!!.fragments.animeList.id)
        }
    }

    override fun getItemCount(): Int = items.size
}