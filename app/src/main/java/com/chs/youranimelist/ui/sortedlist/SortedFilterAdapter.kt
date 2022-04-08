package com.chs.youranimelist.ui.sortedlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.databinding.ItemSortedFilterBinding

class SortedFilterAdapter(
    private val filterList: List<HashMap<String, String>>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<SortedFilterAdapter.SortedFilterViewHolder>() {

    inner class SortedFilterViewHolder(val binding: ItemSortedFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.txtSortedFilterValue.setOnClickListener {
                clickListener.invoke(filterList[layoutPosition].values.toString())
            }
        }

        fun bind(position: Int) {
            binding.txtSortedFilterName.text = filterList[position].keys.
            binding.txtSortedFilterValue.text = filterList[position].values.toString()
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