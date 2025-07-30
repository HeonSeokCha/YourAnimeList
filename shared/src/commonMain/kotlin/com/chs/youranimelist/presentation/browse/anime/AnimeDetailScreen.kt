package com.chs.youranimelist.presentation.browse.anime

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.browse.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.color
import com.chs.youranimelist.presentation.common.ItemSaveButton
import com.chs.youranimelist.presentation.common.ShimmerImage
import com.chs.youranimelist.presentation.common.placeholder
import com.chs.youranimelist.presentation.isNotEmptyValue
import com.chs.youranimelist.presentation.ui.theme.Pink80
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    onLinkClick: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val animeDetailEvent by viewModel.animeDetailEvent.collectAsStateWithLifecycle(AnimeDetailEvent.Idle)

    LaunchedEffect(animeDetailEvent) {
        when (animeDetailEvent) {
            AnimeDetailEvent.OnError -> {}

            else -> Unit
        }
    }

    AnimeDetailScreen(state = state) { event ->
        when (event) {
            AnimeDetailEvent.ClickButton.Close -> {
                onCloseClick()
            }

            is AnimeDetailEvent.ClickButton.Anime -> {
                onAnimeClick(
                    event.id,
                    event.idMal
                )
            }

            is AnimeDetailEvent.ClickButton.Chara -> {
                onCharaClick(event.id)
            }

            is AnimeDetailEvent.ClickButton.Trailer -> {
                onTrailerClick(event.id)
            }

            is AnimeDetailEvent.ClickButton.Genre -> {
                onGenreClick(event.genre)
            }

            is AnimeDetailEvent.ClickButton.SeasonYear -> {
                onSeasonYearClick(event.year, event.season)
            }

            is AnimeDetailEvent.ClickButton.Studio -> {
                onStudioClick(event.id)
            }

            is AnimeDetailEvent.ClickButton.Tag -> {
                onTagClick(event.tag)
            }

            is AnimeDetailEvent.ClickButton.Link -> {
                onLinkClick(event.url)
            }

            else -> viewModel.changeEvent(event)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    state: AnimeDetailState,
    onEvent: (AnimeDetailEvent) -> Unit,
) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(state.selectTabIdx) {
        pagerState.animateScrollToPage(state.selectTabIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(
            AnimeDetailEvent.OnTabSelected(pagerState.currentPage)
        )
    }

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            AnimeDetailHeadBanner(
                animeDetailInfo = state.animeDetailInfo,
                isAnimeSave = state.isSave,
                trailerClick = { trailerId ->
                    if (trailerId != null) {
                        onEvent(AnimeDetailEvent.ClickButton.Trailer(trailerId))
                    }
                }, saveClick = {
                    if (state.animeDetailInfo == null) return@AnimeDetailHeadBanner

                    if (state.isSave!!) {
                        onEvent(AnimeDetailEvent.DeleteAnimeInfo(state.animeDetailInfo.animeInfo))
                    } else {
                        onEvent(AnimeDetailEvent.InsertAnimeInfo(state.animeDetailInfo.animeInfo))
                    }
                }
            )
        },
        content = {
            SecondaryTabRow(
                selectedTabIndex = state.selectTabIdx
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

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {
                        AnimeOverViewScreen(
                            animeDetailState = state.animeDetailInfo,
                            animeThemeState = state.animeThemes,
                            onEvent = { onEvent(it) },
                        )
                    }

                    1 -> {
                        AnimeCharaScreen(state.animeDetailInfo) { id ->
                            onEvent(AnimeDetailEvent.ClickButton.Chara(id))
                        }
                    }

                    2 -> {
                        if (state.animeRecList != null) {
                            AnimeRecScreen(animeRecList = state.animeRecList) { id, idMal ->
                                onEvent(
                                    AnimeDetailEvent.ClickButton.Anime(id = id, idMal = idMal)
                                )
                            }
                        }
                    }
                }
            }
        },
        isShowTopBar = false,
        onCloseClick = {
            onEvent(AnimeDetailEvent.ClickButton.Close)
        }
    )
}

@Composable
private fun AnimeDetailHeadBanner(
    animeDetailInfo: AnimeDetailInfo?,
    isAnimeSave: Boolean?,
    trailerClick: (trailerId: String?) -> Unit,
    saveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
    ) {
        Box {
            ShimmerImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color()
                            ?: LightGray
                    ),
                url = animeDetailInfo?.bannerImage
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
            ShimmerImage(
                modifier = Modifier
                    .width(130.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color()
                            ?: Gray
                    ),
                url = animeDetailInfo?.animeInfo?.imageUrl
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = 146.dp,
                    end = 8.dp,
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