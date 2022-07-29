package com.chs.youranimelist.presentation.charaList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
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
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.ui.theme.Pink80

@Composable
fun ItemYourChara(
    character: CharacterDto,
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
            .height(200.dp)
            .clickable {
                clickAble()
            },
        backgroundColor = Pink80,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp),
                model = character.image,
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