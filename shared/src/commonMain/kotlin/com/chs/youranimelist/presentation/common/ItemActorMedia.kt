package com.chs.youranimelist.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import presentation.UiConst
import presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemActorMedia(
    info: Pair<CharacterInfo, AnimeInfo>?,
    onAnimeClick: (Int, Int) -> Unit,
    onCharaClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(280.dp)
            .clickable {
                if (info?.first == null) return@clickable

                onCharaClick(info.first.id)
            },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(200.dp)
            ) {
                ShimmerImage(
                    modifier = Modifier
                        .width(130.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    url = info?.first?.imageUrl
                )

                ShimmerImage(
                    modifier = Modifier
                        .width(60.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(bottomEnd = 5.dp))
                        .border(
                            width = 2.dp,
                            color = Color.White,
                        )
                        .align(AbsoluteAlignment.BottomRight)
                        .clickable {
                            if (info?.second == null) return@clickable

                            onAnimeClick(
                                info.second.id,
                                info.second.idMal
                            )
                        },
                    url = info?.second?.imageUrl
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                modifier = Modifier
                    .placeholder(visible = info == null),
                text = info?.first?.name ?: UiConst.TITLE_PREVIEW,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier
                    .placeholder(visible = info == null),
                text = info?.second?.title ?: UiConst.TITLE_PREVIEW,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewItemActorMedia() {
    YourAnimeListTheme {
        val animeInfo = AnimeInfo(
            id = 0,
            idMal = 0,
            title = "test1",
            imageUrl = null,
            imagePlaceColor = null,
            averageScore = 0,
            favourites = 80,
            seasonYear = 0,
            season = "",
            format = "TV",
            status = "RELEASING"
        )

        val charaInfo = CharacterInfo(
            id = 0,
            name = "test2",
            nativeName = "test",
            imageUrl = null,
            favourites = 123
        )

        ItemActorMedia(
            info = charaInfo to animeInfo,
            onAnimeClick = { id, idmal ->

            }
        ) {

        }
    }
}
