package com.chs.youranimelist.ui.browse.anime.overview

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.util.ConvertDate

object AnimeOverviewBinding {

    @BindingAdapter("animeOverviewHtmlConvert")
    @JvmStatic
    fun animeOverviewHtmlConvert(textView: TextView, string: String?) {
        if (string?.isNotEmpty() == true)
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    @BindingAdapter("animeOverviewScoreConvert")
    @JvmStatic
    fun animeOverviewScoreConvert(textView: TextView, string: String?) {
        if (string.isNullOrEmpty()) {
            textView.text = "0%"
        } else {
            textView.text = "$string%"
        }
    }

    @BindingAdapter("animeOverviewEnglishNull")
    @JvmStatic
    fun animeOverviewEnglishNull(textView: TextView, title: AnimeOverviewQuery.Title?) {
        if (title?.english.isNullOrEmpty()) {
            textView.text = "${title?.romaji}"
        } else {
            textView.text = "${title?.english}"
        }
    }

    @BindingAdapter("animeOverviewEpisode")
    @JvmStatic
    fun animeOverviewEpisode(textView: TextView, episode: Int?) {
        if (episode == null) textView.text = "?"
        else textView.text = episode.toString()
    }

    @BindingAdapter("animeOverviewDurationVisible")
    @JvmStatic
    fun animeOverviewDurationVisible(textView: TextView, duration: Int?) {
        if (duration == null) {
            textView.isVisible = false
        }
    }

    @BindingAdapter("animeOverviewDurationValue")
    @JvmStatic
    fun animeOverviewDurationValue(textView: TextView, duration: Int?) {
        textView.text = "$duration mins"
    }

    @BindingAdapter("animeOverviewStatusValue")
    @JvmStatic
    fun animeOverviewStatusValue(textView: TextView, status: String?) {
        textView.text = status?.replace("_", " ") ?: ""
    }

    @BindingAdapter("animeOverviewDateText")
    @JvmStatic
    fun animeOverviewDateText(textView: TextView, date: Any?) {
        when(date) {
            is AnimeDetailQuery.StartDate -> {
                textView.text = if (date.day != null) {
                    ConvertDate.convertToDateFormat(date.year, date.month, date.day)
                } else "?"
            }
            is AnimeDetailQuery.EndDate -> {
                textView.text = if (date.day != null) {
                    ConvertDate.convertToDateFormat(date.year, date.month, date.day)
                } else "?"
            }
        }
    }

    @BindingAdapter("animeOverviewSeasonValue")
    @JvmStatic
    fun animeOverviewSeasonValue(textView: TextView, anime: AnimeOverviewQuery.Media?) {
        textView.text = if (anime?.seasonYear != null) {
            "${anime?.format}" + " ⦁ ${anime?.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @BindingAdapter("animeOverviewCoverImage")
    @JvmStatic
    fun animeOverviewCoverImage(imageView: ImageView, path: String?) {
        imageView.load(path) {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
            size(260, 540)
        }
    }
}