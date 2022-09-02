package com.chs.youranimelist.presentation.browse.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.ui.theme.Purple200
import com.chs.youranimelist.ui.theme.Pink80
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
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val overViewScroll = rememberLazyListState()
    val charaViewScroll = rememberLazyGridState()
    val recommendScroll = rememberLazyListState()
    var expandDesc by remember { mutableStateOf(false) }

    val tabList = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeDetailInfo(id)
        viewModel.isSaveAnime(id)
    }

    BoxWithConstraints {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            AnimeDetailHeadBanner(viewModel)
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
                                maxLines = 1,
                                color = Purple200,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 13.sp
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
                modifier = Modifier
                    .height(screenHeight)
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
                    }),
                count = tabList.size,
                state = pagerState,
                userScrollEnabled = false,
            ) {
                when (this.currentPage) {
                    0 -> {
                        AnimeOverViewScreen(
                            animeId = id,
                            animeMalId = idMal,
                            navController = navController,
                            scrollState = overViewScroll,
                            expandDesc = expandDesc,
                            changeExpand = { expandDesc = it },
                            calcScreenHeight = {}
                        )
                    }
                    1 -> {
                        AnimeCharaScreen(
                            animeId = id,
                            lazyGridScrollState = charaViewScroll,
                            navController = navController,
                        )
                    }
                    2 -> {
                        AnimeRecScreen(
                            animeId = id,
                            lazyListState = recommendScroll,
                            navController = navController,
                        )
                    }
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
            CircularProgressIndicator(color = Pink80)
        }
    }
}

@Composable
fun AnimeDetailHeadBanner(
    viewModel: AnimeDetailViewModel
) {
    val state = viewModel.state
    val starId = "starId"
    val favoriteId = "favoriteId"
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
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                )
            }),
        Pair(
            favoriteId,
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Box {
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
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .width(130.dp)
                        .height(180.dp)
                        .padding(
                            start = 8.dp,
                        )
                        .clip(RoundedCornerShape(5.dp)),
                    model = state.animeDetailInfo?.media?.coverImage?.extraLarge,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(
                            top = 90.dp,
                            start = 16.dp
                        )
                ) {
                    Text(
                        text = state.animeDetailInfo?.media?.title?.english
                            ?: state.animeDetailInfo?.media?.title?.romaji.toString(),
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
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
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(starId, starId)
                                    append(state.animeDetailInfo?.media?.averageScore.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        Text(
                            text = buildAnnotatedString {
                                appendInlineContent(favoriteId, favoriteId)
                                append(state.animeDetailInfo?.media?.favourites.toString())
                            },
                            inlineContent = inlineContent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                onClick = {
                    if (state.animeDetailInfo != null) {
                        if (state.isSaveAnime != null) {
                            viewModel.deleteAnime(state.isSaveAnime)
                        } else {
                            viewModel.insertAnime()
                        }
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
}



