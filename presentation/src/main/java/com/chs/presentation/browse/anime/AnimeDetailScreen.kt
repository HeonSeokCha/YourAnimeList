package com.chs.presentation.browse.anime

import android.app.Activity
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.chs.common.Constants
import com.chs.domain.model.AnimeDetailInfo
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.CollapsingToolbarScaffold
import com.chs.presentation.browse.MediaDetailEvent
import com.chs.presentation.color
import com.chs.presentation.common.ItemSaveButton
import com.chs.presentation.common.LoadingIndicator
import com.chs.presentation.common.PlaceholderHighlight
import com.chs.presentation.common.placeholder
import com.chs.presentation.common.shimmer
import com.chs.presentation.isNotEmptyValue
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.launch

@Composable
fun AnimeDetailScreen(
    navController: NavController,
    state: AnimeDetailState,
    onEvent: (MediaDetailEvent) -> Unit
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    CollapsingToolbarScaffold(
        scrollState = scrollState,
        header = {
            AnimeDetailHeadBanner(
                animeDetailInfo = state.animeDetailInfo,
                isAnimeSave = state.isSave,
                trailerClick = { trailerId ->
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(
                            context,
                            "${Constants.YOUTUBE_BASE_URL}$trailerId".toUri()
                        )
                }, saveClick = {
                    if (state.isSave) {
                        onEvent(MediaDetailEvent.InsertMediaInfo)
                    } else {
                        onEvent(MediaDetailEvent.DeleteMediaInfo)
                    }
                }
            )
        }, onCloseClick = {
            activity?.finish()
        },
        stickyHeader = {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Pink80
                    )
                }
            ) {
                state.tabNames.forEachIndexed { index, title ->
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
    ) {

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    AnimeOverViewScreen(
                        animeOverViewInfo = state.animeDetailInfo,
                        animeTheme = state.animeThemes,
                    ) {
                        navController.navigate(it)
                    }
                }

                1 -> {
                    AnimeCharaScreen(
                        charaInfoList = state.animeDetailInfo?.characterList
                            ?: List<CharacterInfo?>(6) { null },
                    ) {
                        navController.navigate(it)
                    }
                }

                2 -> {
                    if (state.animeId != null) {
                        AnimeRecScreen(
                            animeRecList = state.animeRecList,
                            navController = navController,
                        )
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
                    .placeholder(
                        visible = animeDetailInfo == null,
                        highlight = PlaceholderHighlight.shimmer()
                    )
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color
                            ?: LightGray
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
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .placeholder(
                            visible = animeDetailInfo == null,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                        .width(130.dp)
                        .height(180.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            color = animeDetailInfo?.animeInfo?.imagePlaceColor?.color
                                ?: LightGray
                        ),
                    model = animeDetailInfo?.animeInfo?.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(
                            top = 104.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .placeholder(
                                visible = animeDetailInfo == null,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                        text = animeDetailInfo?.animeInfo?.title ?: "title PreView Title PreView",
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )


                    Text(
                        modifier = Modifier
                            .placeholder(
                                visible = animeDetailInfo == null,
                                highlight = PlaceholderHighlight.shimmer()
                            )
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
                        Text(
                            modifier = Modifier
                                .placeholder(
                                    visible = animeDetailInfo == null,
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
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

                        Text(
                            modifier = Modifier
                                .placeholder(
                                    visible = animeDetailInfo == null,
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
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
                isSave = isAnimeSave
            ) {
                saveClick()
            }
        }
    }
}