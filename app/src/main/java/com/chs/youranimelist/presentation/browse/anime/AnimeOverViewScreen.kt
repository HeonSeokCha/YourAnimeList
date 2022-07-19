package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollographql.apollo3.api.label
import com.chs.youranimelist.util.Constant.GENRE_COLOR
import com.chs.youranimelist.util.color
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeOverViewScreen(
    animeId: Int,
    viewModel: AnimeOverViewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeOverView(animeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FlowRow(
            modifier = Modifier
                .padding(start = 8.dp),
            mainAxisSpacing = 4.dp
        ) {
            state.animeOverViewInfo?.media?.genres?.forEach { genre ->
                Chip(
                    onClick = { },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = GENRE_COLOR[genre]?.color ?: Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = genre.toString(),)
                }
            }
        }

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Text(
            text = state.animeOverViewInfo?.media?.description.toString(),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )

    }
}