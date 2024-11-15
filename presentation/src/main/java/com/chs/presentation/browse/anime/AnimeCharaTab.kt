package com.chs.presentation.browse.anime

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import coil.compose.AsyncImage
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.common.PlaceholderHighlight
import com.chs.presentation.common.placeholder
import com.chs.presentation.common.shimmer

@Composable
fun AnimeCharaScreen(
    state: Resource<AnimeDetailInfo>,
    onClick: (BrowseScreen.CharacterDetailScreen) -> Unit
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
        when (state) {
            is Resource.Loading -> {
                items(12) {
                    CharaImageItem(charaInfo = null) { }
                }
            }

            is Resource.Success -> {
                val charaInfoList = state.data?.characterList ?: emptyList()
                items(charaInfoList) { charaInfo ->
                    CharaImageItem(charaInfo = charaInfo) {
                        onClick(it)
                    }
                }
            }

            is Resource.Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "Something Wrong for Loading List."
                    )
                }
            }
        }
    }
}

@Composable
fun CharaImageItem(
    charaInfo: CharacterInfo?,
    onClick: (BrowseScreen.CharacterDetailScreen) -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable {
                if (charaInfo != null) {
                    onClick(
                        BrowseScreen.CharacterDetailScreen(id = charaInfo.id)
                    )
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100)),
            placeholder = ColorPainter(Color.LightGray),
            model = charaInfo?.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier
                .placeholder(visible = charaInfo == null),
            text = charaInfo?.name ?: "Character",
            textAlign = TextAlign.Center
        )
    }
}
