package com.chs.youranimelist.presentation.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.databinding.ItemCharacterListBinding

class CharacterListAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CharacterListViewHolder(private val binding: ItemCharacterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).charaId)
            }
        }

        fun bind() {
            binding.model = getItem(layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind()
    }
}