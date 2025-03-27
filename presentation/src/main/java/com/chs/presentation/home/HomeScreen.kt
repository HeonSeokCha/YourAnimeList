package com.chs.presentation.home

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.main.Screen

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onAnimeClick: (Int, Int) -> Unit,
    onSortScreenClick: (Screen.SortList) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(HomeEvent.Idle)
    val context = LocalContext.current

    LaunchedEffect(event) {
        when (event) {
            is HomeEvent.ShowToast -> {
                Toast.makeText(
                    context,
                    (event as HomeEvent.ShowToast).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    HomeScreen(state = state) { sideEffect ->
        when (sideEffect) {
            is HomeEvent.Navigate.Sort -> {
                onSortScreenClick(
                    Screen.SortList(
                        year = sideEffect.year,
                        season = sideEffect.season,
                        sortOption = sideEffect.option
                    )
                )
            }

            is HomeEvent.Navigate.Anime -> {
                onAnimeClick(sideEffect.id, sideEffect.idMal)
            }

            else -> Unit
        }
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    event: (HomeEvent) -> Unit,
) {
    val pagerState = rememberPagerState { UiConst.BANNER_SIZE }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            item {
                ItemHomeBanner(banner = null) { _, _ -> }
            }

            items(6) { idx ->
                ItemRecommendCategory(
                    title = state.animeCategoryList[idx],
                    list = List<AnimeInfo?>(UiConst.MAX_BANNER_SIZE) { null },
                    sortClick = { }, animeClick = { id, idMal -> }
                )
            }
        } else {
            val data = state.animeRecommendList
            item {
                if (data?.bannerList != null) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth(),
                        key = { data.bannerList[it].animeInfo.id }
                    ) { idx ->
                        ItemHomeBanner(
                            banner = data.bannerList[idx]
                        ) { id, idMal ->
                            event(HomeEvent.Navigate.Anime(id = id, idMal = idMal))
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
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
            }

            items(
                data?.animeBasicList?.size ?: UiConst.MAX_BANNER_SIZE,
                key = { state.animeCategoryList[it].first }
            ) { idx ->
                ItemRecommendCategory(
                    title = state.animeCategoryList[idx],
                    list = data?.animeBasicList?.get(idx)
                        ?: List<AnimeInfo?>(UiConst.MAX_BANNER_SIZE) { null },
                    sortClick = {
                        event(
                            HomeEvent.Navigate.Sort(
                                year = it.year,
                                season = it.season,
                                option = it.sortOption
                            )
                        )
                    }, animeClick = { id, idMal ->
                        event(
                            HomeEvent.Navigate.Anime(id = id, idMal = idMal)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ItemRecommendCategory(
    title: Pair<String, Screen.SortList>,
    list: List<AnimeInfo?>,
    sortClick: (Screen.SortList) -> Unit,
    animeClick: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title.first,
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = {
                sortClick(title.second)
            }
        ) {
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
        items(
            count = list.size,
            key = { list[it]?.id ?: it }
        ) {
            val item = list[it]
            ItemAnimeSmall(
                item = item,
                onClick = {
                    if (item != null) {
                        animeClick(item.id, item.idMal)
                    }
                }
            )
        }
    }
}