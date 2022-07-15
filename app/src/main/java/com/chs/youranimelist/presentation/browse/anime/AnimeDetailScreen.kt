package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.ui.theme.Purple200
import com.chs.youranimelist.util.color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimeDetailScreen(
    id: Int,
    idMal: Int,
    navController: NavController,
    viewModel: AnimeDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val tabList = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeDetailInfo(id)
        viewModel.isSaveAnime(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        AnimeDetailHeadBanner(
            viewModel
        )

        Spacer(
            modifier = Modifier
                .padding(top = 16.dp)
        )
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = Purple200
                )
            }
        ) {
            tabList.forEachIndexed { index, s ->
                Tab(
                    text = {
                        Text(
                            text = tabList[index],
                            color = Purple200
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxHeight()
                .nestedScroll(remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            return if (available.y > 0) Offset.Zero else Offset(
                                x = 0f,
                                y = -scrollState.dispatchRawDelta(-available.y)
                            )
                        }
                    }
                })
        ) {
            when (this.currentPage) {
                0 -> {
                    AnimeOverViewScreen(id)
                }
                1 -> {
                    AnimeCharaScreen(id)
                }
                2 -> {
                    AnimeRecScreen()
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Magenta)
        }
    }
}

@Composable
fun AnimeDetailHeadBanner(
    viewModel: AnimeDetailViewModel
) {
    val state = viewModel.state
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    color = state.animeDetailInfo?.media?.coverImage?.color?.color
                        ?: "#ffffff".color
                ),
            model = state.animeDetailInfo?.media?.bannerImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (state.animeDetailInfo?.media?.trailer != null) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Filled.PlayArrow, null)
            }
        }

        AsyncImage(
            modifier = Modifier
                .width(130.dp)
                .height(180.dp)
                .padding(
                    start = 8.dp,
                )
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(5.dp)),
            model = state.animeDetailInfo?.media?.coverImage?.extraLarge,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 154.dp)
        ) {
            Text(
                text = state.animeDetailInfo?.media?.title?.english
                    ?: state.animeDetailInfo?.media?.title?.romaji.toString(),
                fontSize = 18.sp
            )

            if (state.animeDetailInfo?.media?.seasonYear != null) {
                Text(text = "${state.animeDetailInfo.media.format?.name} ⦁ ${state.animeDetailInfo.media.seasonYear}")
            } else {
                Text(text = state.animeDetailInfo?.media?.format?.name ?: "")
            }

            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (state.animeDetailInfo?.media?.averageScore != null) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = state.animeDetailInfo.media.averageScore.toString(),
                        fontWeight = FontWeight.Bold,

                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                }

                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))
                Text(
                    text = state.animeDetailInfo?.media?.favourites.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                if (state.isSaveAnime != null) {
                    viewModel.deleteAnime(state.isSaveAnime)
                } else {
                    viewModel.insertAnime(state.animeDetailInfo?.media!!)
                }
            }
        ) {
            if (state.isSaveAnime != null) {
                Text("SAVED")
            } else {
                Text("ADD MY LIST")
            }
        }
    }
}



