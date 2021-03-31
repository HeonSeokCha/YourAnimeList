package com.chs.youranimelist.ui.browse.anime.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.databinding.ItemCharacterBinding

class AnimeCharaAdapter(
    private val items: List<AnimeCharacterQuery.CharactersNode?>,
    private val clickListener: (charaId: Int) -> Unit
) : RecyclerView.Adapter<AnimeCharaAdapter.CharaViewHolder>() {

    inner class CharaViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(items[layoutPosition]!!.id)
            }
        }

        fun bind() {
            binding.model = items[layoutPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharaViewHolder {
        val view = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharaViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size
}