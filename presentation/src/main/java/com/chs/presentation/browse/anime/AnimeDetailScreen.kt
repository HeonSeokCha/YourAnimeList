package com.chs.presentation.browse.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseScreen
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.color
import com.chs.presentation.common.ItemSaveButton
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.common.placeholder
import com.chs.presentation.isNotEmptyValue
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimeDetailScreenRoot(
    viewModel: AnimeDetailViewModel,
    onTrailerClick: (String) -> Unit,
    onAnimeClick: (Int, Int) -> Unit,
    onCharaClick: (Int) -> Unit,
    onGenreClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onSeasonYearClick: (Int, String) -> Unit,
    onStudioClick: (Int) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimeDetailScreen(state = state) { event ->
        when (event) {
            AnimeDetailEvent.OnCloseClick -> {
                onCloseClick()
            }

            is AnimeDetailEvent.OnAnimeClick -> {
                onAnimeClick(
                    event.id,
                    event.idMal
                )
            }

            is AnimeDetailEvent.OnCharaClick -> {
                onCharaClick(event.id)
            }

            is AnimeDetailEvent.OnTrailerClick -> {
                onTrailerClick(event.id)
            }

            is AnimeDetailEvent.OnGenreClick -> {
                onGenreClick(event.genre)
            }

            is AnimeDetailEvent.OnSeasonYearClick -> {
                onSeasonYearClick(event.year, event.season)
            }

            is AnimeDetailEvent.OnStudioClick -> {
                onStudioClick(event.id)
            }

            is AnimeDetailEvent.OnTagClick -> {
                onTagClick(event.tag)
            }

            else -> Unit
        }
        viewModel.changeEvent(event)
    }
}

@Composable
fun AnimeDetailScreen(
    state: AnimeDetailState,
    onEvent: (AnimeDetailEvent) -> Unit,
) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }


    LaunchedEffect(state.selectTabIdx) {
        pagerState.animateScrollToPage(state.selectTabIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(
            AnimeDetailEvent.OnTabSelected(pagerState.currentPage)
        )
    }

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                pagerState.scrollToPage(0)
                onEvent(AnimeDetailEvent.GetAnimeDetailInfo)
                delay(500L)
                isRefreshing = false
            }
        }
    ) {
        CollapsingToolbarScaffold(
            scrollState = scrollState,
            header = {
                when (state.animeDetailInfo) {
                    is Resource.Loading -> {
                        AnimeDetailHeadBanner(
                            animeDetailInfo = null,
                            isAnimeSave = false,
                            trailerClick = {},
                            saveClick = {}
                        )
                    }

                    is Resource.Success -> {
                        val data = state.animeDetailInfo.data
                        if (data != null) {
                            AnimeDetailHeadBanner(
                                animeDetailInfo = data,
                                isAnimeSave = state.isSave,
                                trailerClick = { trailerId ->
                                    if (trailerId != null) {
                                        onEvent(
                                            AnimeDetailEvent.OnTrailerClick(trailerId)
                                        )
                                    }
                                }, saveClick = {
                                    if (state.isSave) {
                                        onEvent(AnimeDetailEvent.DeleteAnimeInfo(data.animeInfo))
                                    } else {
                                        onEvent(AnimeDetailEvent.InsertAnimeInfo(data.animeInfo))
                                    }
                                }
                            )
                        }
                    }

                    is Resource.Error -> {

                    }
                }
            },
            isShowTopBar = false,
            onCloseClick = {
                onEvent(AnimeDetailEvent.OnCloseClick)
            },
            stickyHeader = {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = state.selectTabIdx,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectTabIdx]),
                            color = Pink80
                        )
                    }
                ) {
                    UiConst.ANIME_DETAIL_TAB_LIST.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp,
                                )
                            },
                            selected = state.selectTabIdx == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = Pink80,
                            unselectedContentColor = Gray
                        )
                    }
                }
            }
        ) {

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {
                        AnimeOverViewScreen(
                            animeDetailState = state.animeDetailInfo,
                            animeThemeState = state.animeThemes,
                            onTagClick = { tag ->
                                onEvent(
                                    AnimeDetailEvent.OnTagClick(tag)
                                )
                            },
                            onAnimeClick = { id, idMal ->
                                onEvent(
                                    AnimeDetailEvent.OnAnimeClick(id = id, idMal = idMal)
                                )
                            },
                            onStudioClick = { id ->
                                onEvent(
                                    AnimeDetailEvent.OnStudioClick(id)
                                )
                            },
                            onSeasonYearClick = { year, season ->
                                onEvent(
                                    AnimeDetailEvent.OnSeasonYearClick(year = year, season = season)
                                )
                            },
                            onGenreClick = { genre ->
                                onEvent(
                                    AnimeDetailEvent.OnGenreClick(genre = genre)
                                )
                            }
                        )
                    }

                    1 -> {
                        AnimeCharaScreen(state = state.animeDetailInfo) { id ->
                            onEvent(
                                AnimeDetailEvent.OnCharaClick(id)
                            )
                        }
                    }

                    2 -> {
                        AnimeRecScreen(animeRecList = state.animeRecList) { id, idMal ->
                            onEvent(
                                AnimeDetailEvent.OnAnimeClick(id = id, idMal = idMal)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeDetailHeadBanner(
    animeDetailInfo: AnimeDetailInfo?,
    isAnimeSave: Boolean,
    trailerClick: (trailerId: String?) -> Unit,
    saveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color
                            ?: LightGray
                    )
                    .placeholder(visible = animeDetailInfo == null),
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
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 160.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(130.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color
                            ?: Gray
                    )
                    .placeholder(visible = animeDetailInfo == null),
                model = animeDetailInfo?.animeInfo?.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = 146.dp,
                    top = 258.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .placeholder(visible = animeDetailInfo == null),
                text = animeDetailInfo?.animeInfo?.title ?: "title PreView Title PreView",
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )


            Text(
                modifier = Modifier
                    .placeholder(visible = animeDetailInfo == null)
                    .padding(top = 8.dp),
                text = if (animeDetailInfo?.animeInfo?.seasonYear.isNotEmptyValue
                    && animeDetailInfo?.animeInfo?.status != null
                ) {
                    "${animeDetailInfo.animeInfo.format} ⦁ ${animeDetailInfo.animeInfo.seasonYear}"
                } else {
                    animeDetailInfo?.animeInfo?.format ?: "Title PreView"
                }
            )

            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (animeDetailInfo?.animeInfo?.averageScore.isNotEmptyValue) {
                    Text(
                        modifier = Modifier
                            .placeholder(visible = animeDetailInfo == null),
                        text = buildAnnotatedString {
                            appendInlineContent(
                                UiConst.AVERAGE_SCORE_ID,
                                UiConst.AVERAGE_SCORE_ID
                            )
                            append("${animeDetailInfo?.animeInfo?.averageScore ?: 0}")
                        },
                        inlineContent = UiConst.inlineContent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }


                if (animeDetailInfo?.animeInfo?.favourites.isNotEmptyValue) {
                    Text(
                        modifier = Modifier
                            .placeholder(visible = animeDetailInfo == null),
                        text = buildAnnotatedString {
                            appendInlineContent(
                                UiConst.FAVOURITE_ID,
                                UiConst.FAVOURITE_ID
                            )
                            append("${animeDetailInfo?.animeInfo?.favourites ?: 0}")

                        },
                        inlineContent = UiConst.inlineContent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    )
                }
            }
        }

        ItemSaveButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            isSave = isAnimeSave
        ) {
            saveClick()
        }
    }
}


@Preview
@Composable
private fun PreviewAnimeDetail() {
    val animeInfo = AnimeInfo(
        id = 0,
        idMal = 0,
        title = "TEST123\n123",
        imageUrl = null,
        imagePlaceColor = null,
        averageScore = 0,
        favourites = 80,
        seasonYear = 2024,
        season = "",
        format = "TV",
        status = "RELEASING"
    )
    AnimeDetailHeadBanner(
        animeDetailInfo = AnimeDetailInfo(
            animeInfo = animeInfo,
            titleEnglish = "",
            titleNative = "",
            description = "",
            startDate = "",
            endDate = "",
            trailerInfo = null,
            bannerImage = "",
            type = "",
            genres = null,
            tags = null,
            episode = 0,
            duration = 0,
            chapters = 0,
            popularScore = 0,
            meanScore = 0,
            source = "",
            animeRelationInfo = listOf(),
            studioInfo = null,
            characterList = listOf()
        ),
        isAnimeSave = false,
        trailerClick = {},
    ) {

    }
}