package com.chs.youranimelist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
        var temp: String = ""
        when(any) {
            is AnimeListQuery.Medium -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeListQuery.Medium1 -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeDetailQuery.Media -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeRecListQuery.TrendingMedium -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeRecListQuery.PopularMedium -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeRecListQuery.UpcommingMedium -> {
                temp = any.coverImage?.extraLarge!!
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                temp = any.coverImage?.extraLarge!!
            }
        }
        Glide.with(imageView.context).load(temp)
            .transform(RoundedCorners(10))
            .into(imageView)
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("imageGenericBannerSrc")
    @JvmStatic
    fun loadGenericBannerImg(imageView: ImageView,any: Any?) {
        var temp: String = ""
        var color: String = ""
        when(any) {
            is AnimeDetailQuery.Media -> {
                temp = any.bannerImage.toString()
                color = any.coverImage!!.color.toString()
            }
            is AnimeRecListQuery.Medium -> {
                temp = any.bannerImage.toString()
                color = any.coverImage!!.color.toString()
            }
        }
        Glide.with(imageView.context).load(temp)
            .transform(RoundedCorners(10))
//            .placeholder(ColorDrawable(Color.parseColor(color)))
            .into(imageView)
    }

    @SuppressLint("ResourceAsColor")
    @BindingAdapter("textGenericStatus")
    @JvmStatic
    fun setGenericTextStatus(textView: TextView, any: Any?) {
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

    @BindingAdapter("textGenericScore")
    @JvmStatic
    fun setGenericTextScore(textView: TextView,any: Any?) {
        var temp: String = "0"
        when (any) {
            is AnimeDetailQuery.Media -> {
                temp  = "${any.averageScore}"
            }
            is AnimeListQuery.Medium -> {
                temp  = "${any.averageScore}"
            }
            is AnimeListQuery.Medium1 -> {
                temp  = "${any.averageScore}"
            }
            is AnimeRecListQuery.Medium -> {
                temp  = "${any.averageScore}"
            }
            is AnimeRecListQuery.TrendingMedium -> {
                temp  = "${any.averageScore}"
            }
            is AnimeRecListQuery.PopularMedium -> {
                temp  = "${any.averageScore}"
            }
            is AnimeRecListQuery.AlltimeMedium -> {
                temp  = "${any.averageScore}"
            }
        }
        if(temp == "null") {
            temp = "0"
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