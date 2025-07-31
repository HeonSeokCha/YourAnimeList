package com.chs.youranimelist.presentation.common

import androidx.compose.runtime.Composable
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
