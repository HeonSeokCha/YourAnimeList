package com.chs.youranimelist.ui.search.manga


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.SearchMangaQuery
import com.chs.youranimelist.databinding.ItemSearchBinding

class SearchMangaAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchMangaQuery.Medium, SearchMangaAdapter.ViewHolder>(SearchMangaDiffUtil()) {

    inner class ViewHolder(private val binding: ItemSearchBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).fragments.animeList.id.toLong()
}