package com.chs.youranimelist.presentation.browse.anime

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.youranimelist.presentation.LoadingIndicator
import com.chs.youranimelist.presentation.shimmerEffect
import com.chs.youranimelist.presentation.ui.theme.Pink80
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.color
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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
    val tabList = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeDetailInfo(id)
        viewModel.getAnimeTheme(idMal)
        viewModel.isSaveAnime(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            stickyHeader {
                AnimeDetailHeadBanner(
                    state = state,
                    trailerClick = { trailerId ->
                        CustomTabsIntent.Builder()
                            .build()
                            .launchUrl(
                                context,
                                Uri.parse("${Constant.YOUTUBE_BASE_URL}$trailerId")
                            )
                    },
                    insertClick = {
                        viewModel.insertAnime()
                    },
                    deleteClick = {
                        if (state.isSaveAnime != null) {
                            viewModel.deleteAnime(state.isSaveAnime)
                        }
                    }
                )
            }
            item {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Pink80
                        )
                    }
                ) {
                    tabList.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp,
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = Pink80,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }
            item {
                HorizontalPager(
                    state = pagerState,
                    pageCount = tabList.size,
                    modifier = Modifier
                        .fillMaxSize(),
                    userScrollEnabled = false
                ) {
                    when (pagerState.currentPage) {
                        0 -> {
                            AnimeOverViewScreen(
                                animeOverViewInfo = state.animeDetailInfo,
                                animeTheme = state.animeThemes,
                                navController = navController
                            )
                        }
                        1 -> {
                            AnimeCharaScreen(
                                animeCharaInfo = state.animeDetailInfo?.media?.characters,
                                navController = navController,
                            )
                        }
                        2 -> {
                            AnimeRecScreen(
                                animeId = id,
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingIndicator()
    }
}

@Composable
fun AnimeDetailHeadBanner(
    state: AnimeDetailState,
    trailerClick: (trailerId: String?) -> Unit,
    insertClick: () -> Unit,
    deleteClick: () -> Unit
) {
    val activity = (LocalContext.current as? Activity)
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
            }
        ),
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
            }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Box {
            AsyncImage(
                modifier = if (state.isLoading) {
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .shimmerEffect()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(
                            color = state.animeDetailInfo?.media?.coverImage?.color?.color
                                ?: "#ffffff".color
                        )
                },
                model = state.animeDetailInfo?.media?.bannerImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.TopStart),
                onClick = { activity?.finish() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = null
                )
            }

            if (state.animeDetailInfo?.media?.trailer != null) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClick = {
                        trailerClick(state.animeDetailInfo.media.trailer.id)
                    }
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
                    modifier = if (state.isLoading) {
                        Modifier
                            .width(130.dp)
                            .height(180.dp)
                            .padding(
                                start = 8.dp,
                            )
                            .shimmerEffect()
                    } else {
                        Modifier
                            .width(130.dp)
                            .height(180.dp)
                            .padding(
                                start = 8.dp,
                            )
                            .clip(RoundedCornerShape(5.dp))
                    },
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
                            ?: (state.animeDetailInfo?.media?.title?.romaji ?: ""),
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
                                    append(state.animeDetailInfo.media.averageScore.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }

                        if (state.animeDetailInfo?.media?.favourites != null) {
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(favoriteId, favoriteId)
                                    append(state.animeDetailInfo.media.favourites.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                        }
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
                            deleteClick()
                        } else {
                            insertClick()
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
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(height)
//            .alpha(1f - progress)
//            .background(MaterialTheme.colors.primarySurface),
//        horizontalArrangement = Arrangement.Start,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        IconButton(
//            modifier = Modifier.padding(start = 4.dp),
//            onClick = { activity?.finish() }
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Close,
//                tint = Color.White,
//                contentDescription = null
//            )
//        }
//    }
}