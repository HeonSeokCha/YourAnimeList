package com.chs.youranimelist.ui.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.databinding.ItemViewPagerBinding

class HomeRecViewPagerAdapter(
    private val items: List<HomeRecommendListQuery.Medium?>,
    private val mContext: Context,
    private val clickListener: (animeId: Int) -> Unit
) : RecyclerView.Adapter<HomeRecViewPagerAdapter.ViewPagerViewHolder>() {

    class ViewPagerViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.model = items[position]
        holder.binding.root.setOnClickListener {
            clickListener.invoke(items[position]!!.id)
        }
        Glide.with(mContext).load(items[position]!!.coverImage!!.extraLarge)
            .override(750, 250)
            .placeholder(
                ColorDrawable(
                    Color.parseColor(
                        items[position]!!.coverImage?.color ?: "#ffffff"
                    )
                )
            )
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.imageView2)
    }

    override fun getItemCount(): Int = items.size
}


