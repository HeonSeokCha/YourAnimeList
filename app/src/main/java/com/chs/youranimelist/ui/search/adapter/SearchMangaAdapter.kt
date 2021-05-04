package com.chs.youranimelist.ui.search.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchMediaBinding
import com.chs.youranimelist.network.response.SearchResult

class SearchMangaAdapter(
    private val list: List<SearchResult?>,
    private val clickListener: (id: Int,idMal: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    class SearchMangaViewHolder(val binding: ItemSearchMediaBinding) :
        RecyclerView.ViewHolder(binding.root)

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view =
                ItemSearchMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SearchMangaViewHolder(view)
        } else {
            val view =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchMangaViewHolder) {
            holder.binding.model = list[position]!!.animeSearchResult!!.fragments.animeList
            holder.binding.root.setOnClickListener {
                clickListener.invoke(
                    list[position]!!.animeSearchResult!!.fragments.animeList.id,
                    list[position]!!.animeSearchResult!!.fragments.animeList.idMal ?: 0
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = list.size

}
