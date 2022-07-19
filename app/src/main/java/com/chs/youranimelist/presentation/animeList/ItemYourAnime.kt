package com.chs.youranimelist.presentation.animeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.youranimelist.data.model.AnimeDto
import com.chs.youranimelist.ui.theme.Pink80
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.color
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemYourAnime(anime: AnimeDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        backgroundColor = Pink80,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .background(
                        color = "#ffffff".color
                    ),
                model = anime.coverImage,
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
                    text = anime.title,
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
                    text = "${anime.seasonYear} ⦁ ${anime.format}",
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
                        text = anime.averageScore.toString(),
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
                        text = anime.favorites.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

//                FlowRow {
//                    anime.genre.forEach { genre ->
//                        Chip(
//                            onClick = { },
//                            colors = ChipDefaults.chipColors(
//                                backgroundColor = Constant.GENRE_COLOR[genre]?.color ?: Color.Black,
//                                contentColor = Color.White
//                            )
//                        ) {
//                            Text(text = genre.toString(),)
//                        }
//                    }
//                }
            }
        }
    }
}