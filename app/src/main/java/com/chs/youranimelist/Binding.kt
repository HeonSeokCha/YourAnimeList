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
    @BindingAdapter("imageSrc")
    @JvmStatic
    fun loadImg(imageView: ImageView,path: String?) {
        Glide.with(imageView.context).load(path)
            .into(imageView)
    }

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

    @BindingAdapter("imageGenericSrc")
    @JvmStatic
    fun loadGenericImg(imageView: ImageView,any: Any?) {
        when(any) {
            is AnimeListQuery.Medium1 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeListQuery.Medium2 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeListQuery.Medium3 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
            is AnimeListQuery.Medium4 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .into(imageView)
            }
        }
    }

    @BindingAdapter("textGeneric")
    @JvmStatic
    fun setGenericText(textView: TextView,any: Any?) {
        when (any) {
            is AnimeListQuery.Medium1 -> {
                textView.text = any.title?.romaji
            }
            is AnimeListQuery.Medium2 -> {
                textView.text = any.title?.romaji
            }
            is AnimeListQuery.Medium3 -> {
                textView.text = any.title?.romaji
            }
            is AnimeListQuery.Medium4 -> {
                textView.text = any.title?.romaji
            }
        }
    }
}