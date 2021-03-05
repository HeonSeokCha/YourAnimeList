package com.chs.youranimelist.ui.search.character


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.databinding.ItemSearchCharacterBinding

class SearchCharacterAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchCharacterQuery.Character, SearchCharacterAdapter.ViewHolder>(
    SearchCharacterDiffUtil()
) {

    inner class ViewHolder(private val binding: ItemSearchCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).id)
            }
        }

        fun bind() {
            binding.model = getItem(layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemSearchCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()
}