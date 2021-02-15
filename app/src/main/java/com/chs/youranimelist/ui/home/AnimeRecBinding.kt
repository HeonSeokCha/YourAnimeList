package com.chs.youranimelist.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeRecListQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.fragment.AnimeList
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

    @BindingAdapter("animeEnglishNull")
    @JvmStatic
    fun animeEnglishNull(textView: TextView, title: AnimeList.Title?) {
        if(title!!.english.isNullOrEmpty()) {
            textView.text = "${title.romaji}"
        } else {
            textView.text = "${title.english}"
        }
    }

    @BindingAdapter("animeImageCover")
    @JvmStatic
    fun animeImageCover(imageView: ImageView, path: String?) {
        Glide.with(imageView.context).load(path)
            .transform(RoundedCorners(10))
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    @BindingAdapter("animeFormatYear")
    @JvmStatic
    fun animeFormatYear(textView: TextView, anime: AnimeList?) {
        textView.text = "${anime!!.format} ⦁ ${anime!!.seasonYear}"
    }

    @BindingAdapter("animeStatusValue")
    @JvmStatic
    fun animeStatusValue(textView: TextView, status: String) {
        var temp: String = ""
        when(status) {
            "RELEASING" -> {
                temp = "Releasing"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.releasing))
            }
            "FINISHED" -> {
                temp = "Finished"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.finished))
            }
            "NOT_YET_RELEASED" -> {
                temp = "Up Coming"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.notYet))
            }
        }
        textView.text = temp
    }

    @BindingAdapter("animeScoreVisible")
    @JvmStatic
    fun animeScoreVisible(textView: TextView, score: Int?) {
        if(score == null) {
            textView.isVisible = false
        } else {
            textView.text = score.toString()
        }
    }
}