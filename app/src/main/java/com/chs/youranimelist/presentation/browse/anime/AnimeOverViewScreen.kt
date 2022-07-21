package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.youranimelist.util.Constant.GENRE_COLOR
import com.chs.youranimelist.util.color
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeOverViewScreen(
    animeId: Int,
    viewModel: AnimeOverViewViewModel = hiltViewModel(),
    scrollState: ScrollState,
    expandDesc: Boolean,
    changeExpand: (Boolean) -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeOverView(animeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .verticalScroll(scrollState),
    ) {
        FlowRow(
            modifier = Modifier,
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
                    Text(text = genre.toString())
                }
            }
        }

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        if (expandDesc) {
            Text(
                text = state.animeOverViewInfo?.media?.description ?: "",
            )
        } else {
            Text(
                text = state.animeOverViewInfo?.media?.description ?: "",
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                changeExpand(!expandDesc)
            }) {
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        }
    }
}