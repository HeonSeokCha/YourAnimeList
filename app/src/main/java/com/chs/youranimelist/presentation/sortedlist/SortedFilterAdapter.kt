package com.chs.youranimelist.presentation.sortedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemSortedFilterBinding

class SortedFilterAdapter(
    private val filterList: List<String>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<SortedFilterAdapter.SortedFilterViewHolder>() {

    private val itemTitleList: List<String> by lazy {
        if (filterList.size > 1) {
            listOf("Year", "Season", "Sort")
        } else {
            listOf("Genre")
        }
    }

    inner class SortedFilterViewHolder(val binding: ItemSortedFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.txtSortedFilterValue.setOnClickListener {
                clickListener.invoke(itemTitleList[layoutPosition])
            }
        }

        fun bind(position: Int) {
            binding.txtSortedFilterName.text = itemTitleList[position]
            binding.txtSortedFilterValue.text = filterList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortedFilterViewHolder {
        val view =
            ItemSortedFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SortedFilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SortedFilterViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = filterList.size

}