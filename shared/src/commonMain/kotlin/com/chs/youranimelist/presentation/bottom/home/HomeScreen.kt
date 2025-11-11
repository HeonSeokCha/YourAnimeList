package com.chs.youranimelist.presentation.bottom.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CategoryType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.main.Screen

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onNavigateAnimeDetail: (Int, Int) -> Unit,
    onNavigateSort: (Screen.SortList) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateAnimeDetail -> {
                    onNavigateAnimeDetail(effect.id, effect.idMal)
                }

                is HomeEffect.NavigateSort -> {
                    onNavigateSort(
                        Screen.SortList(
                            year = effect.year,
                            season = effect.season,
                            sortOption = effect.option
                        )
                    )
                }
            }
        }
    }

    HomeScreen(
        state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val pagerState = rememberPagerState { UiConst.BANNER_SIZE }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            state.isLoading || state.animeRecommendList == null -> {
                item {
                    ItemHomeBanner()
                }

                items(state.animeCategoryList.size) { idx ->
                    ItemRecommendCategory(info = state.animeCategoryList[idx])
                }
            }

            state.isError -> {

            }

            else -> {
                val data = state.animeRecommendList
                item {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = pagerState,
                        userScrollEnabled = false,
                        key = { it }
                    ) { idx ->
                        ItemHomeBanner(banner = data.bannerList[idx]) { id, idMal ->
                            onIntent(HomeIntent.ClickAnime(id = id, idMal = idMal))
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(data.bannerList.size) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) Color.DarkGray
                                else Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(10.dp)
                            )
                        }
                    }
                }

                items(
                    data.animeBasicList.size,
                    key = { state.animeCategoryList[it].title }
                ) { idx ->
                    ItemRecommendCategory(
                        info = state.animeCategoryList[idx],
                        list = data.animeBasicList[idx],
                        clickAnime = { id, idMal ->
                            onIntent(HomeIntent.ClickAnime(id = id, idMal = idMal))
                        },
                        clickCategoryType = { onIntent(HomeIntent.ClickCategory(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemRecommendCategory(
    info: CategoryType,
    list: List<AnimeInfo?> = emptyList(),
    clickAnime: (Int, Int) -> Unit = { _, _ -> },
    clickCategoryType: (CategoryType) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                start = 8.dp,
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = info.title,
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = { clickCategoryType(info) }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = Color.Gray,
                contentDescription = null
            )
        }
    }

    LazyRow(
        modifier = Modifier
            .padding(bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (list.isEmpty()) {
            items(count = UiConst.MAX_BANNER_SIZE) {
                ItemAnimeSmall()
            }
        } else {
            items(
                count = list.size,
                key = { list[it]?.id ?: it }
            ) {
                val item = list[it]
                ItemAnimeSmall(
                    item = item,
                    onClick = {
                        if (item == null) return@ItemAnimeSmall
                        clickAnime(item.id, item.idMal)
                    }
                )
            }
        }
    }
}