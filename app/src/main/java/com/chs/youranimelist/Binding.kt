package com.chs.youranimelist

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

object Binding {

    @BindingAdapter("trailerBtn")
    @JvmStatic
    fun trailerChk(floatingActionButton: FloatingActionButton, trailer: String?) {
        if(trailer == null) floatingActionButton.visibility = View.GONE
    }

    @BindingAdapter("htmlTags")
    @JvmStatic
    fun setHtmlTags(textView: TextView,string: String?) {
        if(string?.isNotEmpty() == true)
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    @BindingAdapter("imageGenericCoverSrc")
    @JvmStatic
    fun loadGenericCoverImg(imageView: ImageView,any: Any?) {
        when(any) {
            is AnimeListQuery.Medium -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeListQuery.Medium1 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeDetailQuery.Media -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeRecListQuery.Medium1 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeRecListQuery.Medium2 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeRecListQuery.Medium3 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeRecListQuery.Medium4 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
        }
    }

    @BindingAdapter("imageGenericBannerSrc")
    @JvmStatic
    fun loadGenericBannerImg(imageView: ImageView,any: Any?) {
        when(any) {
            is AnimeDetailQuery.Media -> {
                Glide.with(imageView.context).load(any.bannerImage)
                    .into(imageView)
            }
            is AnimeRecListQuery.Medium -> {
                Glide.with(imageView.context).load(any.bannerImage)
                    .into(imageView)
            }
        }
    }

    @BindingAdapter("textGeneric")
    @JvmStatic
    fun setGenericText(textView: TextView,any: Any?) {
        when (any) {
            is AnimeDetailQuery.Media -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeListQuery.Medium -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeListQuery.Medium1 -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium1 -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium2 -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium3 -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium4 -> {
                if (any.title?.english != null) {
                    textView.text = any.title?.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
        }
    }
}