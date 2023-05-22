package com.chs.presentation.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.common.UiConst
import com.chs.domain.model.AnimeRecommendBannerInfo
import com.chs.presentation.browse.BrowseActivity
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun ItemHomeBanner(
    context: Context,
    banner: AnimeRecommendBannerInfo?
) {
    val favoriteId = "favoriteId"
    val scoreId = "scoreId"
    val inlineContent = mapOf(
        Pair(
            scoreId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }
        ),
        Pair(
            favoriteId,
            InlineTextContent(
                Placeholder(
                    width = 1.5.em,
                    height = 1.5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
        ),
    )

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .placeholder(
                visible = banner == null
            ),
        model = banner?.animeInfo?.imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (banner != null) {
                    context.startActivity(
                        Intent(
                            context, BrowseActivity::class.java
                        ).apply {
                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                            this.putExtra(UiConst.TARGET_ID, banner.animeInfo.id)
                            this.putExtra(UiConst.TARGET_ID_MAL, banner.animeInfo.idMal)
                        }
                    )
                }
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    bottom = 32.dp
                )
                .placeholder(
                    visible = banner == null,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            text = banner?.animeInfo?.title ?: "",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 8.dp,
                    bottom = 8.dp
                ),
        ) {
            Text(
                modifier = Modifier
                    .placeholder(
                        visible = banner == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                text = buildAnnotatedString {
                    appendInlineContent(scoreId, scoreId)
                    append((banner?.animeInfo?.averageScore ?: 0).toString())
                },
                inlineContent = inlineContent,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .placeholder(
                        visible = banner == null,
                        highlight = PlaceholderHighlight.shimmer()
                    ),
                text = buildAnnotatedString {
                    appendInlineContent(favoriteId, favoriteId)
                    append((banner?.animeInfo?.favourites ?: 0).toString())
                },
                inlineContent = inlineContent,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}