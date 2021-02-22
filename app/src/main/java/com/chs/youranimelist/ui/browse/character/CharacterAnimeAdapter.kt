package com.chs.youranimelist.ui.browse.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.databinding.ItemAnimeBinding

class CharacterAnimeAdapter(
    private val items: List<CharacterQuery.Edge?>,
    private val clickListener: (id: Int) -> Unit
) :
    RecyclerView.Adapter<CharacterAnimeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(items[layoutPosition]!!.node!!.fragments.animeList.id)
            }
        }

        fun bind() {
            binding.model = items[layoutPosition]!!.node!!.fragments.animeList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size
}