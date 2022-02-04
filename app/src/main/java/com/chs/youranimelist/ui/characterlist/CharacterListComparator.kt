package com.chs.youranimelist.ui.characterlist

import androidx.recyclerview.widget.DiffUtil
import com.chs.youranimelist.data.domain.model.Character

class CharacterListComparator : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}