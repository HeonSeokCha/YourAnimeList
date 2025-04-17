package com.chs.presentation.common

import androidx.compose.runtime.Composable
import com.chs.presentation.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.isNotEmptyValue

@Composable
fun ItemAnimeLarge(
    anime: AnimeInfo?,
    clickAble: () -> Unit
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
