package com.chs.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.ui.theme.Pink80
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun <T> SearchMediaItem(
    item: T,
    clickAble: () -> Unit
) {
    when (item) {
        is AnimeInfo? -> {
            ItemAnimeLarge(anime = item) {
                clickAble()
            }
        }

        is CharacterInfo? -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clickable {
                        clickAble()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Pink80
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Row {
                    AsyncImage(
                        modifier = Modifier
                            .width(150.dp)
                            .height(200.dp)
                            .placeholder(item == null),
                        model = item?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 8.dp,
                                top = 8.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .placeholder(item == null),
                            text = item?.name ?: "PreView Name",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier
                                .placeholder(item == null),
                            text = item?.nativeName ?: "PreView NativeName",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )


                        Text(
                            modifier = Modifier
                                .placeholder(item == null),
                            text = buildAnnotatedString {
                                appendInlineContent(
                                    UiConst.FAVOURITE_ID,
                                    UiConst.FAVOURITE_ID
                                )
                                append("${item?.favourites ?: 0}")
                            },
                            inlineContent = UiConst.inlineContent,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}