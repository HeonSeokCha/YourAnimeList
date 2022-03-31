package com.chs.youranimelist.ui.search.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchCharacterBinding
import com.chs.youranimelist.search.SearchCharacterQuery

class SearchCharacterAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchCharacterQuery.Character, RecyclerView.ViewHolder>(diffUtil) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<SearchCharacterQuery.Character>() {
            override fun areItemsTheSame(
                oldItem: SearchCharacterQuery.Character,
                newItem: SearchCharacterQuery.Character
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchCharacterQuery.Character,
                newItem: SearchCharacterQuery.Character
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class SearchCharacterViewHolder(val binding: ItemSearchCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).id)
            }
        }

        fun bind(items: SearchCharacterQuery.Character) {
            binding.model = items
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

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
        if (holder is SearchCharacterViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        Log.e("onViewDetachedFromWindow", holder.layoutPosition.toString())
    }

}