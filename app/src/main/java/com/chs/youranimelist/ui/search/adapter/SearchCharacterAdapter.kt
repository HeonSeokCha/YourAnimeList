package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchCharacterBinding
import com.chs.youranimelist.network.response.SearchResult

class SearchCharacterAdapter(
    private val list: List<SearchResult?>,
    private val clickListener: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }


    class SearchCharacterViewHolder(val binding: ItemSearchCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

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
            holder.binding.model = list[position]!!.charactersSearchResult
            holder.binding.root.setOnClickListener {
                clickListener.invoke(list[position]!!.charactersSearchResult!!.id)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = list.size

}