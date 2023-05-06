package com.chs.presentation.browse.anime.detail

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.common.URLConst
import com.chs.domain.model.AnimeDetailInfo
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.CollapsingAppBar
import com.chs.presentation.browse.anime.AnimeCharaScreen
import com.chs.presentation.browse.anime.overView.AnimeOverViewScreen
import com.chs.presentation.browse.anime.recommend.AnimeRecScreen
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.color
import com.chs.presentation.isNotEmptyValue
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    id: Int,
    idMal: Int,
    navController: NavController,
    viewModel: AnimeDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabList = listOf(
        "OVERVIEW",
        "CHARACTER",
        "RECOMMEND"
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(viewModel, context) {
        viewModel.getAnimeDetailInfo(id)
        viewModel.getAnimeTheme(idMal)
        viewModel.isSaveAnime(id)
    }

    Scaffold(
        topBar = {
            CollapsingAppBar(scrollBehavior = scrollBehavior,
                collapsingContent = {
                    AnimeDetailHeadBanner(
                        animeDetailInfo = state.animeDetailInfo,
                        isAnimeSave = state.isSave,
                        trailerClick = { trailerId ->
                            CustomTabsIntent.Builder()
                                .build()
                                .launchUrl(
                                    context,
                                    Uri.parse("${URLConst.YOUTUBE_BASE_URL}$trailerId")
                                )
                        },
                        closeClick = {
                            activity?.finish()
                        },
                        saveClick = {
                            if (state.isSave) {
                                viewModel.deleteAnime()
                            } else {
                                viewModel.insertAnime()
                            }
                        }
                    )
                }, toolBarClick = {
                    activity?.finish()
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            item {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
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
                    when (it) {
                        0 -> {
                            AnimeOverViewScreen(
                                animeOverViewInfo = state.animeDetailInfo,
                                animeTheme = state.animeThemes,
                                navController = navController
                            )
                        }

                        1 -> {
                            if (!state.animeDetailInfo?.characterList.isNullOrEmpty()) {
                                AnimeCharaScreen(
                                    charaInfoList = state.animeDetailInfo?.characterList!!,
                                    navController = navController,
                                )
                            }
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

        if (state.isLoading) {
            LoadingIndicator()
        }
    }
}

@Composable
fun AnimeDetailHeadBanner(
    animeDetailInfo: AnimeDetailInfo?,
    isAnimeSave: Boolean,
    closeClick: () -> Unit,
    trailerClick: (trailerId: String?) -> Unit,
    saveClick: () -> Unit
) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color
                            ?: "#ffffff".color
                    ),
                model = animeDetailInfo?.bannerImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            if (animeDetailInfo?.trailerInfo != null) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClick = {
                        trailerClick(animeDetailInfo.trailerInfo!!.id)
                    }
                ) {
                    Icon(Icons.Filled.PlayArrow, null)
                }
            }

            IconButton(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        start = 4.dp
                    )
                    .align(Alignment.TopStart),
                onClick = { closeClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    tint = White,
                    contentDescription = null
                )
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
                    model = animeDetailInfo?.animeInfo?.imageUrl,
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
                        text = animeDetailInfo?.animeInfo?.title ?: "",
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )

                    if (animeDetailInfo?.animeInfo?.seasonYear.isNotEmptyValue
                        && animeDetailInfo?.animeInfo?.status != null
                    ) {
                        Text(text = "${animeDetailInfo.animeInfo.format} ⦁ ${animeDetailInfo.animeInfo.seasonYear}")
                    } else {
                        Text(text = animeDetailInfo?.animeInfo?.format ?: "")
                    }

                    Row(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        if (animeDetailInfo?.animeInfo?.averageScore != null) {
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(starId, starId)
                                    append(animeDetailInfo.animeInfo.averageScore.toString())
                                },
                                inlineContent = inlineContent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }

                        if (animeDetailInfo?.animeInfo?.favourites != null) {
                            Text(
                                text = buildAnnotatedString {
                                    appendInlineContent(favoriteId, favoriteId)
                                    append(animeDetailInfo.animeInfo.favourites.toString())
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
                onClick = { saveClick() }
            ) {
                if (isAnimeSave) {
                    Text("SAVED")
                } else {
                    Text("ADD MY LIST")
                }
            }
        }
    }
}