package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchCharacterQuery
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchCharacterBinding

class SearchCharacterAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchCharacterQuery.Character, RecyclerView.ViewHolder>(SearchCharacterDiffUtil()) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }


    inner class SearchCharacterViewHolder(private val binding: ItemSearchCharacterBinding) :
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

    inner class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = ItemSearchCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            SearchCharacterViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchCharacterViewHolder) holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}