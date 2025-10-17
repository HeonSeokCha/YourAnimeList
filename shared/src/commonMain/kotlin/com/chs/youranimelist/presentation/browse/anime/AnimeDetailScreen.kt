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
import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeDetailInfo
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.SeasonType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.CollapsingToolbarScaffold
import com.chs.youranimelist.presentation.color
import com.chs.youranimelist.presentation.common.ItemSaveButton
import com.chs.youranimelist.presentation.common.ShimmerImage
import com.chs.youranimelist.presentation.common.shimmer
import com.chs.youranimelist.presentation.isNotEmptyValue
import com.chs.youranimelist.presentation.ui.theme.Pink80
import kotlinx.coroutines.flow.Flow
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
    onSeasonYearClick: (String, Int) -> Unit,
    onStudioClick: (Int) -> Unit,
    onLinkClick: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AnimeDetailEffect.NavigateAnimeDetail -> onAnimeClick(effect.id, effect.idMal)
                is AnimeDetailEffect.NavigateBrowser -> onLinkClick(effect.url)
                is AnimeDetailEffect.NavigateCharaDetail -> onCharaClick(effect.id)
                AnimeDetailEffect.NavigateClose -> onCloseClick()
                is AnimeDetailEffect.NavigateSortGenre -> onGenreClick(effect.genre)
                is AnimeDetailEffect.NavigateSortSeasonYear -> {
                    onSeasonYearClick(effect.seasonType, effect.year)
                }

                is AnimeDetailEffect.NavigateSortTag -> onTagClick(effect.tag)
                is AnimeDetailEffect.NavigateStudio -> onStudioClick(effect.id)
                is AnimeDetailEffect.NavigateYouTube -> onTrailerClick(effect.id)
            }
        }
    }

    AnimeDetailScreen(
        state = state,
        animeRecPaging = viewModel.animeRecPaging,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    state: AnimeDetailState,
    animeRecPaging: Flow<PagingData<AnimeInfo>>,
    onIntent: (AnimeDetailIntent) -> Unit,
) {
    val pagerState = rememberPagerState { 3 }
    val scrollState = rememberScrollState()

    LaunchedEffect(state.selectTabIdx) {
        pagerState.animateScrollToPage(state.selectTabIdx)
    }


    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            AnimeDetailHeadBanner(
                info = state.animeDetailInfo,
                isAnimeSave = state.isSave,
                onIntent = onIntent
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
                        onClick = { onIntent(AnimeDetailIntent.OnTabSelected(index)) },
                        selectedContentColor = Pink80,
                        unselectedContentColor = Gray
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                key = { it }
            ) { page ->
                when (page) {
                    0 -> {
                        AnimeOverViewScreen(
                            animeDetailState = state.animeDetailInfo,
                            animeThemeState = state.animeThemes,
                            onIntent = onIntent,
                        )
                    }

                    1 -> {
                        AnimeCharaScreen(
                            info = state.animeDetailInfo,
                            onIntent = onIntent
                        )
                    }

                    2 -> {
                        AnimeRecScreen(
                            state = state,
                            animeRecList = animeRecPaging,
                            onIntent = onIntent
                        )
                    }
                }
            }
        },
        isShowTopBar = false,
        onCloseClick = { onIntent(AnimeDetailIntent.ClickClose) }
    )
}

@Composable
private fun AnimeDetailHeadBanner(
    info: AnimeDetailInfo?,
    isAnimeSave: Boolean,
    onIntent: (AnimeDetailIntent) -> Unit
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
                        color = info?.animeInfo?.imagePlaceColor?.color()
                            ?: LightGray
                    ),
                url = info?.bannerImage
            )

            if (info?.trailerInfo != null) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.Center),
                    onClick = { onIntent(AnimeDetailIntent.CLickTrailer(info.trailerInfo.id!!)) }
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
                        color = info?.animeInfo?.imagePlaceColor?.color()
                            ?: Gray
                    ),
                url = info?.animeInfo?.imageUrl
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
                    .shimmer(visible = info == null),
                text = info?.animeInfo?.title ?: "title PreView Title PreView",
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )


            Text(
                modifier = Modifier
                    .shimmer(visible = info == null)
                    .padding(top = 8.dp),
                text = if (info?.animeInfo?.seasonYear.isNotEmptyValue
                    && info?.animeInfo?.status != null
                ) {
                    "${info.animeInfo.format} ⦁ ${info.animeInfo.seasonYear}"
                } else {
                    info?.animeInfo?.format ?: "Title PreView"
                }
            )

            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                if (info?.animeInfo?.averageScore.isNotEmptyValue) {
                    Text(
                        modifier = Modifier
                            .shimmer(visible = info == null),
                        text = buildAnnotatedString {
                            appendInlineContent(
                                UiConst.AVERAGE_SCORE_ID,
                                UiConst.AVERAGE_SCORE_ID
                            )
                            append("${info?.animeInfo?.averageScore ?: 0}")
                        },
                        inlineContent = UiConst.inlineContent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }


                if (info?.animeInfo?.favourites.isNotEmptyValue) {
                    Text(
                        modifier = Modifier
                            .shimmer(visible = info == null),
                        text = buildAnnotatedString {
                            appendInlineContent(
                                UiConst.FAVOURITE_ID,
                                UiConst.FAVOURITE_ID
                            )
                            append("${info?.animeInfo?.favourites ?: 0}")

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
            isSave = isAnimeSave,
            saveClick = {
                if (info == null) return@ItemSaveButton
                onIntent(AnimeDetailIntent.ClickSaved(info.animeInfo))
            }
        )
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
        info = AnimeDetailInfo(
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
    ) {

    }
}