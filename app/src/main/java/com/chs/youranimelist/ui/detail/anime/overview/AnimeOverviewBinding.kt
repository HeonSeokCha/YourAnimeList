package com.chs.youranimelist.ui.detail.anime.overview

import android.text.Html
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.ConvertDate

object AnimeOverviewBinding {

    @BindingAdapter("animeOverviewHtmlConvert")
    @JvmStatic
    fun animeOverviewHtmlConvert(textView: TextView, string: String?) {
        if(string?.isNotEmpty() == true)
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    @BindingAdapter("animeOverviewScoreConvert")
    @JvmStatic
    fun animeOverviewScoreConvert(textView: TextView, string: String?) {
        if(string.isNullOrEmpty()) {
            textView.text = "0%"
        } else {
            textView.text = "$string%"
        }
    }

    @BindingAdapter("animeOverviewEnglishNull")
    @JvmStatic
    fun animeOverviewEnglishNull(textView: TextView, title: AnimeDetailQuery.Title?) {
        if(title!!.english.isNullOrEmpty()) {
            textView.text = "${title.romaji}"
        } else {
            textView.text = "${title.english}"
        }
    }

    @BindingAdapter("animeOverviewEpisode")
    @JvmStatic
    fun animeOverviewEpisode(textView: TextView, episode: Int?) {
        if(episode == null) textView.text = "?"
        else textView.text = episode.toString()
    }

    @BindingAdapter("animeOverviewDurationVisible")
    @JvmStatic
    fun animeOverviewDurationVisible(textView: TextView, duration: Int?) {
        if(duration == null) {
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
    fun animeOverviewStatusValue(textView: TextView, status: String) {
        textView.text = status.replace("_"," ")
    }

    @BindingAdapter("animeOverviewDateText")
    @JvmStatic
    fun animeOverviewDateText(textView: TextView,date: Any?) {
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

    @BindingAdapter("animeOverviewSeasonValue")
    @JvmStatic
    fun animeOverviewSeasonValue(textView: TextView, anime: AnimeDetailQuery.Media) {
        textView.text = "${anime.season} ${anime.seasonYear}"
    }

}