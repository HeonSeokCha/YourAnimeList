package com.chs.youranimelist.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.chs.domain.model.CharacterInfo
import presentation.ui.theme.YourAnimeListTheme

@Composable
fun ItemCharaLarge(
    character: CharacterInfo?,
    onClick: (Int) -> Unit
) {
    ItemCardLarge(
        imageUrl = character?.imageUrl,
        title = character?.name,
        subTitle = character?.nativeName,
        scoreTitle = listOf(character?.favourites),
        onClick = {
            if (character == null) return@ItemCardLarge

            onClick(character.id)
        }
    )
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
