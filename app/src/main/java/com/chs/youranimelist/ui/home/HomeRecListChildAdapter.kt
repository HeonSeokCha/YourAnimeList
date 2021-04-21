package com.chs.youranimelist.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.ItemAnimeChildBinding
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.type.MediaStatus

class HomeRecListChildAdapter(
    private val list: List<AnimeList>,
    private val clickListener: (animeId: Int) -> Unit,
    private val mContext: Context
) : RecyclerView.Adapter<HomeRecListChildAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(val binding: ItemAnimeChildBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = ItemAnimeChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            clickListener.invoke(list[position].id)
        }
        with(holder.binding) {

            Glide.with(mContext).load(list[position].coverImage!!.extraLarge)
                .transform(RoundedCorners(10))
                .override(260, 540)
                .transition(DrawableTransitionOptions().crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this.imgAnimeList)

            this.txtAnimeListTitle.text =
                list[position].title?.english ?: list[position].title?.romaji

            this.txtAnimeType.text = if (list[position].seasonYear != null) {
                "${list[position].format}" + " ⦁ ${list[position].seasonYear}"
            } else {
                "${list[position].format}"
            }

            this.txtAnimeStatus.apply {
                when (list[position].status) {
                    MediaStatus.RELEASING -> {
                        this.text = "Releasing"
                        this.setTextColor(ContextCompat.getColor(mContext, R.color.releasing))
                    }
                    MediaStatus.NOT_YET_RELEASED -> {
                        this.text = "Up Coming"
                        this.setTextColor(ContextCompat.getColor(mContext, R.color.notYet))
                    }
                    MediaStatus.FINISHED -> {
                        this.text = "Finished"
                        this.setTextColor(ContextCompat.getColor(mContext, R.color.finished))
                    }
                    else -> ""
                }
            }

            if (list[position].averageScore == null) {
                this.txtAnimeListScore.isVisible = false
            } else {
                this.txtAnimeListScore.text = list[position].averageScore.toString()
            }
        }
    }

    override fun getItemCount(): Int = list.size
}