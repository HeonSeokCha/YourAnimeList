package com.chs.youranimelist.presentation.characterlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.model.Character
import com.chs.youranimelist.databinding.ItemCharacterListBinding

class CharacterListAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(CharacterListComparator()) {

    inner class CharacterListViewHolder(private val binding: ItemCharacterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.model = getItem(layoutPosition)
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).charaId)
            }
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