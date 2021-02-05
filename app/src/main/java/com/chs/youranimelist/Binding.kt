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

    @BindingAdapter("trailerBtn")
    @JvmStatic
    fun trailerChk(floatingActionButton: FloatingActionButton, trailer: String?) {
        floatingActionButton.isVisible = trailer != null
    }

    @BindingAdapter("dateText")
    @JvmStatic
    fun setTextDate(textView: TextView,date: Any?) {
        when(date) {
            is AnimeDetailQuery.StartDate -> {
                textView.text = if(date.day != null) {
                    ConvertDate.convertToDateFormat(date.year,date.month,date.day)
                } else "?"
            }
            is AnimeDetailQuery.EndDate -> {
                textView.text = if (date.day != null) {
                    ConvertDate.convertToDateFormat(date.year, date.month, date.day)
                } else "?"
            }
        }
    }

    @BindingAdapter("underScoreText")
    @JvmStatic
    fun replaceUnderScore(textView: TextView,string: String?) {
        textView.text = string?.replace("_"," ")
    }

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


    @BindingAdapter("htmlTags")
    @JvmStatic
    fun setHtmlTags(textView: TextView,string: String?) {
        if(string?.isNotEmpty() == true)
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    @BindingAdapter("circleSrc")
    @JvmStatic
    fun loadCircleImg(imageView: ImageView,path: String) {
        Glide.with(imageView.context).load(path)
            .apply(RequestOptions().circleCrop())
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
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

    @BindingAdapter("textType")
    @JvmStatic
    fun setGenericTextType(textView: TextView, anime: Any?) {
        when(anime) {
            is AnimeList -> {
                textView.text = "${anime!!.format} ⦁ ${anime!!.seasonYear}"
            }
            is AnimeDetailQuery.Media -> {
                textView.text = "${anime!!.format} ⦁ ${anime!!.seasonYear}"
            }
        }

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