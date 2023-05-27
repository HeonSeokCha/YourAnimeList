package com.chs.presentation.browse.anime.recommend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.isNotEmptyValue
import com.chs.presentation.ui.theme.Pink80
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun ItemAnimeRecommend(
    animeInfo: AnimeInfo?,
    clickAble: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                clickAble()
            },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Pink80
        )
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .placeholder(animeInfo == null),
                model = animeInfo?.imageUrl,
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
                    modifier = Modifier
                        .placeholder(animeInfo == null),
                    text = animeInfo?.title ?: "Title PreView",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (animeInfo?.seasonYear.isNotEmptyValue && animeInfo?.seasonYear != 0) {
                        "${animeInfo?.seasonYear ?: 2000} ⦁ ${animeInfo?.format ?: "UnKnown"}"
                    } else {
                        animeInfo?.format ?: "UnKnown"
                    },
                    color = Color.White,
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )

                    Spacer(modifier = Modifier.padding(end = 8.dp))

                    Text(
                        modifier = Modifier
                            .placeholder(animeInfo == null),
                        text = "${animeInfo?.averageScore ?: 0}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))

                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    Text(
                        modifier = Modifier
                            .placeholder(animeInfo == null),
                        text = "${animeInfo?.favourites ?: 0}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ItemAnimeRecShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {},
        shape = RoundedCornerShape(5.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(16.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(14.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(12.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(14.dp)
                    )

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(14.dp)
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    }
}