package com.chs.youranimelist.presentation.common

import androidx.compose.runtime.Composable
import com.chs.youranimelist.domain.model.AnimeSavedInfo

@Composable
fun ItemAnimeSaved(
    anime: AnimeSavedInfo? = null,
    clickAble: () -> Unit = {}
) {
    ItemCardLarge(
        imageUrl = anime?.imageUrl,
        title = anime?.title,
        subTitle = "",
        scoreTitle = listOf(
            anime?.averageScore,
            anime?.favourites
        ),
        onClick = clickAble
    )
}
