package com.chs.youranimelist.util

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
import com.chs.youranimelist.browse.anime.AnimeDetailQuery
import com.chs.youranimelist.browse.anime.AnimeRecommendQuery
import com.chs.youranimelist.home.HomeRecommendListQuery
import com.chs.youranimelist.util.ConvertDate.secondsToDateTime
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


@BindingAdapter("animeRecImageBanner")
fun ImageView.animeRecImageBanner(anime: HomeRecommendListQuery.Medium) {
    this.load(anime.bannerImage) {
        crossfade(true)
        crossfade(400)
        size(400, 250)
    }
}

@BindingAdapter("animeImageCover")
fun ImageView.animeImageCover(path: String?) {
    this.load(path) {
        crossfade(true)
        crossfade(400)
        size(250, 350)
        transformations(RoundedCornersTransformation(15f))
    }
}

@BindingAdapter("animeStatusValue")
fun TextView.animeStatusValue(status: String) {
    this.text = when (status) {
        "RELEASING" -> {
            this.setTextColor(ContextCompat.getColor(this.context, R.color.releasing))
            "Releasing"
        }
        "FINISHED" -> {
            this.setTextColor(ContextCompat.getColor(this.context, R.color.finished))
            "Finished"
        }
        "NOT_YET_RELEASED" -> {
            this.setTextColor(ContextCompat.getColor(this.context, R.color.notYet))
            "Up Coming"
        }
        else -> ""
    }
}

@BindingAdapter("animeScoreVisible")
fun TextView.animeScoreVisible(score: Int?) {
    if (score == null) {
        this.isVisible = false
    } else {
        this.text = score.toString()
    }
}

@BindingAdapter("animeDetailFormatYear")
fun TextView.animeDetailFormatYear(anime: AnimeDetailQuery.Media?) {
    this.text = if (anime?.seasonYear != null) {
        "${anime.format}" + " ⦁ ${anime.seasonYear}"
    } else {
        "${anime?.format}"
    }
}

@BindingAdapter("animeRecFormatYear")
fun TextView.animeRecFormatYear(anime: AnimeRecommendQuery.MediaRecommendation) {
    this.text = if (anime.seasonYear != null && anime.format != null) {
        "${anime.format}" + " ⦁ ${anime.seasonYear}"
    } else {
        "${anime.format}"
    }
}

@BindingAdapter("animeDetailImageBanner")
fun ImageView.animeDetailImageBanner(anime: AnimeDetailQuery.Media?) {
    this.load(anime?.bannerImage) {
        placeholder(ColorDrawable(Color.parseColor(anime?.coverImage?.color ?: "#D9666F")))
        crossfade(true)
        crossfade(400)
        size(400, 250)
    }
}

@BindingAdapter("animeDetailTrailerVisible")
fun FloatingActionButton.animeDetailTrailerVisible(trailer: AnimeDetailQuery.Trailer?) {
    this.isVisible = trailer != null
}

@BindingAdapter("animeDetailImageCover")
fun ImageView.animeDetailImageCover(path: String?) {
    this.load(path) {
        crossfade(true)
        crossfade(400)
        size(250, 350)
        transformations(RoundedCornersTransformation(15f))
    }
}

@BindingAdapter("animeRecommendImageCover")
fun ImageView.animeRecommendImageCover(path: String?) {
    this.load(path) {
        crossfade(true)
        crossfade(400)
        size(300, 450)
        transformations(RoundedCornersTransformation(topLeft = 15f, bottomLeft = 15f))
    }
}

@BindingAdapter("animeDetailScoreVisible")
fun TextView.animeDetailScoreVisible(score: Int?) {
    if (score == null) {
        this.isVisible = false
    } else {
        this.apply {
            this.isVisible = true
            this.text = score.toString()
        }
    }
}

@BindingAdapter("animeDetailRelationFormat")
fun TextView.animeDetailRelationFormat(string: String) {
    var temp = string.replace("_", " ")
    this.text = temp[0].uppercase() + temp.substring(1, string.length)
        .lowercase(Locale.getDefault())
}

@BindingAdapter("animeDetailAiring")
fun TextView.animeDetailAiring(airingEpisode: AnimeDetailQuery.NextAiringEpisode?) {
    this.text = if (airingEpisode != null) {
        this.isVisible = true
        "Ep ${airingEpisode.episode} on ${airingEpisode.airingAt.secondsToDateTime()}"
    } else ""
}

@BindingAdapter("animeOverviewHtmlConvert")
fun TextView.animeOverviewHtmlConvert(string: String?) {
    this.text = if (string?.isNotEmpty() == true) {
        Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    } else ""
}

@BindingAdapter("animeOverviewScoreConvert")
fun TextView.animeOverviewScoreConvert(string: String?) {
    this.text = if (string.isNullOrEmpty()) {
        "0%"
    } else {
        "$string%"
    }
}

@BindingAdapter("animeOverviewEpisode")
fun TextView.animeOverviewEpisode(episode: Int?) {
    this.text = episode?.toString() ?: "?"
}

@BindingAdapter("animeOverviewDurationVisible")
fun TextView.animeOverviewDurationVisible(duration: Int?) {
    this.isVisible = duration != null
}

@BindingAdapter("animeOverviewDurationValue")
fun TextView.animeOverviewDurationValue(duration: Int?) {
    this.text = "${duration ?: 0} mins"
}

@BindingAdapter("animeOverviewStatusValue")
fun TextView.animeOverviewStatusValue(status: String?) {
    this.text = status?.replace("_", " ") ?: ""
}

@BindingAdapter("animeOverviewEnglishNull")
fun TextView.animeOverviewEnglishNull(title: AnimeDetailQuery.Title?) {
    this.text = if (title?.english.isNullOrEmpty()) {
        "${title?.romaji}"
    } else {
        "${title?.english}"
    }
}

@BindingAdapter("animeOverviewSeasonValue")
fun TextView.animeOverviewSeasonValue(anime: AnimeDetailQuery.Media?) {
    this.text = if (anime?.seasonYear != null) {
        "${anime?.season} ${anime?.seasonYear}"
    } else {
        "${anime?.season}"
    }
}

@BindingAdapter("animeOverviewStartDateText")
fun TextView.animeOverviewStartDateText(date: AnimeDetailQuery.StartDate?) {
    this.text = if (date != null) {
        ConvertDate.convertToDateFormat(
            date.year,
            date.month,
            date.day
        )
    } else "?"
}

@BindingAdapter("animeOverviewEndDateText")
fun TextView.animeOverviewEndDateText(date: AnimeDetailQuery.EndDate?) {
    this.text = if (date != null) {
        ConvertDate.convertToDateFormat(
            date.year,
            date.month,
            date.day
        )
    } else "?"
}


@BindingAdapter("animeOverviewCoverImage")
fun ImageView.animeOverviewCoverImage(path: String?) {
    this.load(path) {
        crossfade(true)
        crossfade(400)
        size(260, 540)
        transformations(RoundedCornersTransformation(15f))
    }
}

@BindingAdapter("animeCharaCircle")
fun ImageView.animeCharaCircle(path: String?) {
    this.load(path) {
        crossfade(true)
        crossfade(400)
        size(200, 200)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("studioMain")
fun TextView.studioMain(studioList: List<AnimeDetailQuery.StudiosEdge?>?) {
    if (studioList != null) {
        for (i in studioList.indices) {
            if (studioList[i]!!.isMain) {
                this.text = studioList[i]!!.studiosNode!!.name
                break
            }
        }
    }
}

@BindingAdapter("backgroundHexColor")
fun MaterialCardView.backgroundHexColor(genreName: String) {
    this.setCardBackgroundColor(Color.parseColor(Constant.GENRE_COLOR[genreName]))
}