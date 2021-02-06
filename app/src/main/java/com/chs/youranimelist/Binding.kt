package com.chs.youranimelist

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chs.youranimelist.fragment.AnimeList
import com.google.android.material.floatingactionbutton.FloatingActionButton

object Binding {

    @BindingAdapter("seasonText")
    @JvmStatic
    fun setTextSeason(textView: TextView,media: Any?) {
        when(media) {
            is AnimeDetailQuery.Media -> {
                textView.text = "${media?.season?.name} ${media?.seasonYear}"
            }
            is AnimeList -> {
                textView.text = "${media?.season?.name} ${media?.seasonYear}"
            }
        }
    }


    @BindingAdapter("imageCoverSrc")
    @JvmStatic
    fun loadGenericCoverImg(imageView: ImageView,path: String?) {
        Glide.with(imageView.context).load(path)
            .transform(RoundedCorners(10))
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("imageGenericBannerSrc")
    @JvmStatic
    fun loadGenericBannerImg(imageView: ImageView,any: Any?) {
        var temp: String = ""
        var color: String = "#ffffff"
        when(any) {
            is AnimeDetailQuery.Media -> {
                temp = any.bannerImage.toString()
                color = any.coverImage?.color.toString()
            }
            is AnimeRecListQuery.Medium -> {
                temp = any.bannerImage.toString()
                color = any.coverImage?.color.toString()
            }
        }
        if(color == "null") {
            color = "#ffffff"
        }
        Glide.with(imageView.context).load(temp)
            .placeholder(ColorDrawable(Color.parseColor(color)))
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    @SuppressLint("ResourceAsColor")
    @BindingAdapter("textGenericStatus")
    @JvmStatic
    fun setGenericTextStatus(textView: TextView, text: String?) {
        var temp: String = ""
        when(text) {
            "RELEASING" -> {
                temp = "Releasing"
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.releasing))
            }
            "FINISHED" -> {
                temp = "Finished"
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.finished))
            }
            "NOT_YET_RELEASED" -> {
                temp = "Up Coming"
                textView.setTextColor(ContextCompat.getColor(textView.context,R.color.notYet))
            }
        }
        textView.text = temp
    }



    @BindingAdapter("textScore")
    @JvmStatic
    fun setGenericTextScore(textView: TextView,any: String?) {
        textView.text = any ?: "0"
    }

    @BindingAdapter("textGenericTitle")
    @JvmStatic
    fun setGenericTextTitle(textView: TextView,anime: Any?) {
        when(anime) {
            is AnimeRecListQuery.Medium -> {
                if (anime.title?.english != null) {
                    textView.text = anime.title?.english
                } else {
                    textView.text = anime.title?.romaji
                }
            }
            is AnimeList -> {
                if (anime.title?.english != null) {
                    textView.text = anime.title?.english
                } else {
                    textView.text = anime.title?.romaji
                }
            }
            is AnimeDetailQuery.Media -> {
                if (anime.title?.english != null) {
                    textView.text = anime.title?.english
                } else {
                    textView.text = anime.title?.romaji
                }
            }
        }
    }
}