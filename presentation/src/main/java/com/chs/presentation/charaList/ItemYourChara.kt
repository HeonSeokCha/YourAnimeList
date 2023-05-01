package com.chs.presentation.charaList

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemYourChara(
    character: CharacterInfo,
    clickAble: () -> Unit
) {

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
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Pink80
        )
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp),
                model = character.imageUrl,
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
                    text = character.name.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = character.nativeName.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )


                Text(
                    text = buildAnnotatedString {
                        appendInlineContent(starId, starId)
                        append(character.favourites.toString())
                    },
                    inlineContent = inlineContent,
                    color = Color.White,
                    fontSize = 14.sp,
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
        ItemYourChara(charaInfo) {

        }
    }
}