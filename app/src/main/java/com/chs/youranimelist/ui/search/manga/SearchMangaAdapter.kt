package com.chs.youranimelist.ui.search.manga


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.databinding.ItemLoadingBinding
import com.chs.youranimelist.databinding.ItemSearchMediaBinding
import com.chs.youranimelist.ui.search.anime.SearchAnimeAdapter

class SearchMangaAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchMangaQuery.Medium, RecyclerView.ViewHolder>(SearchMangaDiffUtil()) {
    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    inner class SearchMangaViewHolder(private val binding: ItemSearchMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickListener.invoke(getItem(layoutPosition).fragments.animeList.id)
            }
        }

        fun bind() {
            binding.model = getItem(layoutPosition).fragments.animeList
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        if (holder is SearchMangaViewHolder) holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).fragments.animeList.id.toLong()

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}
