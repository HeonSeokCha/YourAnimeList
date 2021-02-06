package com.chs.youranimelist.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chs.youranimelist.AnimeRecListQuery
import com.google.android.material.floatingactionbutton.FloatingActionButton

object AnimeRecBinding {

    @BindingAdapter("animeRecTrailerVisible")
    @JvmStatic
    fun animeRecTrailerVisible(floatingActionButton: FloatingActionButton, trailer: AnimeRecListQuery.Trailer?) {
        floatingActionButton.isVisible = trailer != null
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("animeRecImageBanner")
    @JvmStatic
    fun animeRecImageBanner(imageView: ImageView, anime: AnimeRecListQuery.Medium) {
        var color: String? = anime.coverImage?.color ?: "#ffffff"
        Glide.with(imageView.context).load(anime.bannerImage)
            .placeholder(ColorDrawable(Color.parseColor(color)))
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    @BindingAdapter("animeRecEnglishNull")
    @JvmStatic
    fun animeRecEnglishNull(textView: TextView, title: AnimeRecListQuery.Title?) {
        if(title!!.english.isNullOrEmpty()) {
            textView.text = "${title.romaji}"
        } else {
            textView.text = "${title.english}"
        }
    }
}