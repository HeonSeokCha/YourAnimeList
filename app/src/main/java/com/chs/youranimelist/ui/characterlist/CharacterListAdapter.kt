package com.chs.youranimelist.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.data.Character
import com.chs.youranimelist.databinding.ItemCharacterListBinding

class CharacterListAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(CharacterListComparator()) {

    class CharacterListViewHolder(val binding: ItemCharacterListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.binding.model = getItem(position)
        holder.binding.root.setOnClickListener {
            clickListener.invoke(getItem(position).charaId)
        }
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

}