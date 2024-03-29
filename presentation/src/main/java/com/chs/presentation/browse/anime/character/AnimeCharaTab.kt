package com.chs.presentation.browse.anime.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.BrowseScreen
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun AnimeCharaScreen(
    charaInfoList: List<CharacterInfo?>,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        columns = GridCells.Fixed(3)
    ) {
        items(
            charaInfoList,
        ) { charaInfo ->
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        if (charaInfo != null) {
                            navController.navigate(
                                "${BrowseScreen.CharacterDetailScreen.route}/" +
                                        "${charaInfo.id}"
                            )
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100))
                        .placeholder(charaInfo == null),
                    model = charaInfo?.imageUrl,
                    placeholder = ColorPainter(Color.LightGray),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier
                        .placeholder(charaInfo == null),
                    text = charaInfo?.name ?: "Character",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}