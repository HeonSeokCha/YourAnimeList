package com.chs.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
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
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.SearchCharacterQuery
import com.chs.presentation.fragment.AnimeList
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.util.color

@Composable
fun SearchMediaItem(
    item: Any,
    clickAble: () -> Unit
) {
    when (item) {
        is AnimeInfo -> {
            val starId = "starId"
            val favoriteId = "favoriteId"
            val inlineContent = mapOf(
                Pair(
                    starId,
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
                            tint = Color.Yellow,
                        )
                    }),
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
                    })
            )
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
                            .height(190.dp)
                            .background(
                                color = "#ffffff".color
                            ),
                        model = item.coverImage?.extraLarge,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 8.dp,
                                top = 8.dp
                            )
                    ) {
                        Text(
                            text = item.title?.english ?: item.title?.romaji.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        if (item.seasonYear != null) {
                            Text(
                                text = "${item.seasonYear} ⦁ ${item.format}",
                                color = Color.White,
                            )
                        } else {
                            Text(
                                text = "${item.format}",
                                color = Color.White,
                            )
                        }

                        Row(
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(starId, starId)
                                    append(item.averageScore.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(favoriteId, favoriteId)
                                    append(item.favourites.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        }

        is SearchCharacterQuery.Character -> {
            val starId = "starId"
            val inlineContent = mapOf(
                Pair(
                    starId,
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
                    })
            )

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
                            .height(200.dp),
                        model = item.image?.large,
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
                            text = item.name?.full.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = item.name?.native.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )


                        Text(
                            text = buildAnnotatedString {
                                appendInlineContent(starId, starId)
                                append(item.favourites.toString())
                            },
                            inlineContent = inlineContent,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }

                }
            }
        }
    }
}