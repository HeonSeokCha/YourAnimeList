package com.chs.presentation.browse.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.presentation.AnimeRecommendQuery
import com.chs.presentation.shimmerEffect
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.util.color

@Composable
fun ItemAnimeRecommend(
    animeInfo: AnimeRecommendQuery.MediaRecommendation,
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
                    .background(
                        color = animeInfo.coverImage?.color?.color ?: "#ffffff".color
                    ),
                model = animeInfo.coverImage?.extraLarge,
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
                    text = animeInfo.title?.english ?: animeInfo.title?.romaji.toString(),
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

                Text(
                    text = "${animeInfo.seasonYear} ⦁ ${animeInfo.format}",
                    color = Color.White,
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = animeInfo.averageScore.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))

                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = animeInfo.favourites.toString(),
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
                    .shimmerEffect()
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
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(14.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .height(12.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(14.dp)
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(14.dp)
                            .padding(start = 16.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}