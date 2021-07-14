package com.chs.youranimelist.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.chs.youranimelist.*
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.util.ConvertDate.secondsToDateTime
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

object BindingAdapter {

    @SuppressLint("ResourceType")
    @BindingAdapter("animeRecImageBanner")
    @JvmStatic
    fun animeRecImageBanner(imageView: ImageView, anime: HomeRecommendListQuery.Medium) {
        imageView.load(anime.bannerImage) {

            size(400, 250)
        }
    }

    @BindingAdapter("animeImageCover")
    @JvmStatic
    fun animeImageCover(imageView: ImageView, path: String?) {
        imageView.load(path) {
            size(200, 350)
        }
    }

    @BindingAdapter("animeFormatYear")
    @JvmStatic
    fun animeFormatYear(textView: TextView, anime: AnimeList?) {
        textView.text = ""
        if (anime != null) {
            textView.text = if (anime?.seasonYear != null && anime?.format != null) {
                "${anime.format}" + " ⦁ ${anime.seasonYear}"
            } else {
                "${anime.format}"
            }
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

    @BindingAdapter("animeDetailFormatYear")
    @JvmStatic
    fun animeDetailFormatYear(textView: TextView, anime: AnimeDetailQuery.Media?) {
        textView.text = ""
        textView.text = if (anime?.seasonYear != null) {
            "${anime.format}" + " ⦁ ${anime.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @BindingAdapter("animeRecFormatYear")
    @JvmStatic
    fun animeRecFormatYear(textView: TextView, anime: AnimeRecommendQuery.MediaRecommendation) {
        textView.text = ""
        textView.text = if (anime.seasonYear != null && anime.format != null) {
            "${anime.format}" + " ⦁ ${anime.seasonYear}"
        } else {
            "${anime.format}"
        }
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("animeDetailImageBanner")
    @JvmStatic
    fun animeDetailImageBanner(imageView: ImageView, anime: AnimeDetailQuery.Media?) {
        imageView.load(anime?.bannerImage) {
            placeholder(ColorDrawable(Color.parseColor(anime?.coverImage?.color ?: "#D9666F")))
            crossfade(true)
            size(400, 250)
        }
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
        imageView.load(path) {
            size(200, 350)
            crossfade(true)
        }
    }

    @BindingAdapter("animeRecommendImageCover")
    @JvmStatic
    fun animeRecommendImageCover(imageView: ImageView, path: String?) {
        imageView.load(path) {
            crossfade(true)
            size(300, 450)
            transformations(RoundedCornersTransformation(topLeft = 15f, bottomLeft = 15f))
        }
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
        textView.text = temp[0].uppercase() + temp.substring(1, string.length)
            .lowercase(Locale.getDefault())
    }

    @BindingAdapter("animeDetailAiring")
    @JvmStatic
    fun animeDetailAiring(textView: TextView, airingEpisode: AnimeDetailQuery.NextAiringEpisode?) {
        if (airingEpisode != null) {
            textView.text =
                "Ep ${airingEpisode.episode} on ${airingEpisode.airingAt.secondsToDateTime()}"
            textView.isVisible = true
        }
    }

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

    @BindingAdapter("animeOverviewEnglishNull")
    @JvmStatic
    fun animeOverviewEnglishNull(textView: TextView, title: AnimeOverviewQuery.Title?) {
        if (title?.english.isNullOrEmpty()) {
            textView.text = "${title?.romaji}"
        } else {
            textView.text = "${title?.english}"
        }
    }

    @BindingAdapter("animeOverviewSeasonValue")
    @JvmStatic
    fun animeOverviewSeasonValue(textView: TextView, anime: AnimeOverviewQuery.Media?) {
        textView.text = if (anime?.seasonYear != null) {
            "${anime?.season} ${anime?.seasonYear}"
        } else {
            "${anime?.format}"
        }
    }

    @BindingAdapter("animeOverviewDateText")
    @JvmStatic
    fun animeOverviewDateText(textView: TextView, date: Any?) {
        when (date) {
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


    @BindingAdapter("animeOverviewCoverImage")
    @JvmStatic
    fun animeOverviewCoverImage(imageView: ImageView, path: String?) {
        imageView.load(path) {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
            size(260, 540)
        }
    }

    @BindingAdapter("animeCharaCircle")
    @JvmStatic
    fun animeCharaCircle(imageView: ImageView, path: String?) {
        imageView.load(path) {
            transformations(CircleCropTransformation())
            size(200, 200)
            crossfade(true)
        }
    }

    @BindingAdapter("studioMain")
    @JvmStatic
    fun studioMain(textView: TextView, studioList: List<AnimeOverviewQuery.StudiosEdge?>?) {
        if (studioList != null) {
            for (i in studioList.indices) {
                if (studioList[i]!!.isMain) {
                    textView.text = studioList[i]!!.studiosNode!!.name
                    break
                }
            }
        }
    }
}