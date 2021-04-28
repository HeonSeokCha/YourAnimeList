package com.chs.youranimelist.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.chs.youranimelist.HomeRecommendListQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.fragment.AnimeList

object HomeRecBinding {

    @SuppressLint("ResourceType")
    @BindingAdapter("animeRecImageBanner")
    @JvmStatic
    fun animeRecImageBanner(imageView: ImageView, anime: HomeRecommendListQuery.Medium) {
        imageView.load(anime.bannerImage) {
            placeholder(ColorDrawable(Color.parseColor(anime.coverImage?.color ?: "#ffffff")))
            crossfade(true)
            size(750, 250)
            scale(Scale.FIT)
        }
    }

    @BindingAdapter("animeImageCover")
    @JvmStatic
    fun animeImageCover(imageView: ImageView, path: String?) {
        imageView.load(path) {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
            size(260, 540)
        }
    }

    @BindingAdapter("animeFormatYear")
    @JvmStatic
    fun animeFormatYear(textView: TextView, anime: AnimeList?) {
        textView.text = ""
        textView.text = if (anime?.seasonYear != null) {
            "${anime?.format}" + " ⦁ ${anime?.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @BindingAdapter("animeStatusValue")
    @JvmStatic
    fun animeStatusValue(textView: TextView, status: String) {
        when (status) {
            "RELEASING" -> {
                textView.text = "Releasing"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.releasing))
            }
            "FINISHED" -> {
                textView.text = "Finished"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.finished))
            }
            "NOT_YET_RELEASED" -> {
                textView.text = "Up Coming"
                textView.setTextColor(ContextCompat.getColor(textView.context, R.color.notYet))
            }
        }
    }

    @BindingAdapter("animeScoreVisible")
    @JvmStatic
    fun animeScoreVisible(textView: TextView, score: Int?) {
        if (score == null) {
            textView.isVisible = false
        } else {
            textView.text = score.toString()
        }
    }
}