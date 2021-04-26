package com.chs.youranimelist.ui.browse.anime

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.ConvertDate.secondsToDateTime
import com.google.android.material.floatingactionbutton.FloatingActionButton

object AnimeDetailBinding {

    @BindingAdapter("animeDetailFormatYear")
    @JvmStatic
    fun animeDetailFormatYear(textView: TextView, anime: AnimeDetailQuery.Media?) {
        textView.text = ""
        textView.text = if (anime?.seasonYear != null) {
            "${anime?.format}" + " ⦁ ${anime?.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @BindingAdapter("animeRecFormatYear")
    @JvmStatic
    fun animeRecFormatYear(textView: TextView, anime: AnimeRecommendQuery.MediaRecommendation) {
        textView.text = ""
        textView.text = if (anime?.seasonYear != null) {
            "${anime?.format}" + " ⦁ ${anime?.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("animeDetailImageBanner")
    @JvmStatic
    fun animeDetailImageBanner(imageView: ImageView, anime: AnimeDetailQuery.Media?) {
        Glide.with(imageView.context).load(anime?.bannerImage)
            .override(750, 250).centerInside()
            .placeholder(ColorDrawable(Color.parseColor(anime?.coverImage?.color ?: "#ffffff")))
            .transition(DrawableTransitionOptions().crossFade())
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    @BindingAdapter("animeDetailTrailerVisible")
    @JvmStatic
    fun animeDetailTrailerVisible(
        floatingActionButton: FloatingActionButton, trailer: AnimeDetailQuery.Trailer?
    ) {
        floatingActionButton.isVisible = trailer != null
    }

    @BindingAdapter("animeDetailImageCover")
    @JvmStatic
    fun animeDetailImageCover(imageView: ImageView, path: String?) {
        Glide.with(imageView.context).load(path)
            .transform(RoundedCorners(10))
            .override(260, 540).centerInside()
            .transition(DrawableTransitionOptions().crossFade())
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    @BindingAdapter("animeRecommendImageCover")
    @JvmStatic
    fun animeRecommendImageCover(imageView: ImageView, path: String?) {
        Glide.with(imageView.context).load(path)
            .transition(DrawableTransitionOptions().crossFade())
            .override(260, 540).centerInside()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    @BindingAdapter("animeDetailScoreVisible")
    @JvmStatic
    fun animeDetailScoreVisible(textView: TextView, score: Int?) {
        if (score == null) {
            textView.isVisible = false
        } else {
            textView.apply {
                this.isVisible = true
                this.text = score.toString()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @ExperimentalStdlibApi
    @BindingAdapter("animeDetailRelationFormat")
    @JvmStatic
    fun animeDetailRelationFormat(textView: TextView, string: String) {
        var temp = string.replace("_", " ")
        textView.text = temp[0].uppercase() + temp.substring(1, string.length).toLowerCase()
    }

    @BindingAdapter("animeDetailAiring")
    @JvmStatic
    fun animeDetailAiring(textView: TextView, airingEpisode: AnimeDetailQuery.NextAiringEpisode?) {
        if (airingEpisode != null) {
            textView.text =
                "Ep ${airingEpisode?.episode} on ${airingEpisode?.airingAt?.secondsToDateTime()}"
            textView.isVisible = true
        }
    }

}