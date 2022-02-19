package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchCharacterBinding
import com.chs.youranimelist.data.remote.dto.SearchResult

class SearchCharacterAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchResult, RecyclerView.ViewHolder>(SearchAnimeAdapter.diffUtil) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<SearchResult>() {
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return if (oldItem.charactersSearchResult != null && newItem.charactersSearchResult != null) {
                    oldItem.charactersSearchResult!!.id ==
                            newItem.charactersSearchResult!!.id
                } else false
            }

            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return if (oldItem.charactersSearchResult != null && newItem.charactersSearchResult != null) {
                    oldItem.charactersSearchResult!!.id ==
                            newItem.charactersSearchResult!!.id
                } else false
            }
        }
    }


    inner class SearchCharacterViewHolder(val binding: ItemSearchCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition)!!.charactersSearchResult!!.id)
            }
        }

        fun bind(items: SearchResult?) {
            binding.model = items!!.charactersSearchResult
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

}