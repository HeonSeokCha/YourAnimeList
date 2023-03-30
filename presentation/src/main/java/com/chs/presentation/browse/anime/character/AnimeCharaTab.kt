package com.chs.presentation.browse.anime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.BrowseScreen

@Composable
fun AnimeCharaScreen(
    charaInfoList: List<CharacterInfo>,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .height(1020.dp)
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        columns = GridCells.Fixed(3),
    ) {
        items(charaInfoList) { charaInfo ->
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        navController.navigate(
                            "${BrowseScreen.CharacterDetailScreen.route}/" +
                                    "${charaInfo.id}"
                        )
                    }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100)),
                    model = charaInfo.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = charaInfo.name
                )
            }
        }
    }
}