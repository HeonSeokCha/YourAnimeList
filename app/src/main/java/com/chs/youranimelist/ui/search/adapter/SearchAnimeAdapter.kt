package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchMediaBinding
import com.chs.youranimelist.network.response.SearchResult

class SearchAnimeAdapter(
    private val list: List<SearchResult?>,
    private val clickListener: (id: Int, idMal: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    inner class SearchAnimeViewHolder(val binding: ItemSearchMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(
                    list[layoutPosition]!!.animeSearchResult!!.fragments.animeList.id,
                    list[layoutPosition]!!.animeSearchResult!!.fragments.animeList.idMal ?: 0
                )
            }
        }

        fun bind(item: SearchResult?) {
            binding.model = item!!.animeSearchResult!!.fragments.animeList
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemSearchMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SearchAnimeViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchAnimeViewHolder) {
            holder.bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = list[position].hashCode().toLong()

}