package com.chs.youranimelist

import android.annotation.SuppressLint
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeListQuery.Medium1 -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeDetailQuery.Media -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeRecListQuery.TrendingMedium -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeRecListQuery.PopularMedium -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
                    .into(imageView)
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                Glide.with(imageView.context).load(any.coverImage?.extraLarge)
                    .transform(RoundedCorners(10))
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

    @SuppressLint("ResourceAsColor")
    @BindingAdapter("textGenericStatus")
    @JvmStatic
    fun setGenericTextStatus(textView: TextView,any: Any?) {
        var temp: String = ""
        when (any) {
            is AnimeDetailQuery.Media -> {
                temp  = any.status.toString()
            }
            is AnimeListQuery.Medium -> {
                temp  = any.status.toString()
            }
            is AnimeListQuery.Medium1 -> {
                temp  = any.status.toString()
            }
            is AnimeRecListQuery.Medium -> {
                temp  = any.status.toString()
            }
            is AnimeRecListQuery.TrendingMedium -> {
                temp  = any.status.toString()
            }
            is AnimeRecListQuery.PopularMedium -> {
                temp  = any.status.toString()
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                temp  = any.status.toString()
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                temp  = any.status.toString()
            }
        }
        when(temp) {
            "RELEASING" -> {
                temp = "Releasing"
                textView.setTextColor(R.color.releasing)
            }
            "FINISHED" -> {
                temp = "Finished"
                textView.setTextColor(R.color.finished)
            }
            "NOT_YET_RELEASED" -> {
                temp = "Not Yet Released"
                textView.setTextColor(R.color.notYet)
            }
        }
        textView.text = temp
    }

    @BindingAdapter("textGenericType")
    @JvmStatic
    fun setGenericTextType(textView: TextView,any: Any?) {
        var temp: String = ""
        when (any) {
            is AnimeDetailQuery.Media -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeListQuery.Medium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeListQuery.Medium1 -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeRecListQuery.Medium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeRecListQuery.TrendingMedium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeRecListQuery.PopularMedium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                temp  = "${any.format} ⦁ ${any.seasonYear}"
            }
        }
        textView.text = temp
    }

    @BindingAdapter("textGenericTitle")
    @JvmStatic
    fun setGenericTextTitle(textView: TextView,any: Any?) {
        when (any) {
            is AnimeDetailQuery.Media -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeListQuery.Medium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeListQuery.Medium1 -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.Medium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.TrendingMedium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.PopularMedium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                if (any.title?.english != null) {
                    textView.text = any.title.english
                } else {
                    textView.text = any.title?.romaji
                }
            }
        }
    }
}