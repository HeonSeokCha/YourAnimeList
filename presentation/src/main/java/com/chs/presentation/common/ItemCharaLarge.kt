package com.chs.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemCharaLarge(
    character: CharacterInfo?,
    clickAble: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
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
                    .placeholder(
                        visible = character == null,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .width(150.dp)
                    .height(200.dp),
                model = character?.imageUrl,
                placeholder = ColorPainter(Color.LightGray),
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
                        .placeholder(
                            visible = character == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    text = character?.name ?: UiConst.TITLE_PREVIEW,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = character == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    text = character?.nativeName ?: UiConst.TITLE_PREVIEW,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )


                Text(
                    modifier = Modifier
                        .placeholder(
                            visible = character == null,
                            highlight = PlaceholderHighlight.shimmer()
                        ),
                    text = buildAnnotatedString {
                        appendInlineContent(
                            UiConst.FAVOURITE_ID,
                            UiConst.FAVOURITE_ID
                        )
                        append("${character?.favourites ?: 0}")
                    },
                    inlineContent = UiConst.inlineContent,
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemChara() {
    YourAnimeListTheme {
        val charaInfo = CharacterInfo(
            id = 0,
            name = "test",
            nativeName = "test",
            imageUrl = null,
            favourites = 123
        )
        ItemCharaLarge(charaInfo) {

        }
    }
}
