package com.chs.youranimelist.ui.search.anime


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.SearchAnimeQuery
import com.chs.youranimelist.databinding.ItemSearchMediaBinding

class SearchAnimeAdapter(
    private val clickListener: (id: Int) -> Unit
) : ListAdapter<SearchAnimeQuery.Medium, SearchAnimeAdapter.ViewHolder>(SearchAnimeDiffUtil()) {

    inner class ViewHolder(private val binding: ItemSearchMediaBinding) :
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
        val view =
            ItemSearchMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemId(position: Int): Long = getItem(position).fragments.animeList.id.toLong()
}