package com.chs.youranimelist.presentation.common

import androidx.compose.runtime.Composable
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.isNotEmptyValue

@Composable
fun ItemAnimeLarge(
    anime: AnimeInfo? = null,
    clickAble: () -> Unit = {}
) {
    ItemCardLarge(
        imageUrl = anime?.imageUrl,
        title = anime?.title,
        subTitle = if (anime?.seasonYear.isNotEmptyValue) {
            "${anime?.seasonYear ?: 2000} ⦁ " +
                    (UiConst.mediaStatus[anime?.status]?.first)
        } else {
            UiConst.mediaStatus[anime?.status]?.first
        },
        scoreTitle = listOf(
            anime?.averageScore,
            anime?.favourites
        ),
        onClick = clickAble
    )
}
