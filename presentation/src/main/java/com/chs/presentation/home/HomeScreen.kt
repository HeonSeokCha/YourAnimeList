package com.chs.presentation.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.common.ItemErrorImage
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.main.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeState,
    event: (HomeEvent) -> Unit,
    onNavigate: (Screen.SortListScreen) -> Unit,
    onStartActivity: (Int, Int) -> Unit
) {
    val pagerState = rememberPagerState { UiConst.BANNER_SIZE }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                pagerState.scrollToPage(0)
                event(HomeEvent.GetHomeData)
                delay(500L)
                isRefreshing = false
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.animeRecommendList) {
                is Resource.Loading -> {
                    item {
                        ItemHomeBanner(banner = null) { _, _ -> }
                    }

                    items(6) { idx ->
                        ItemRecommendCategory(
                            title = state.animeCategoryList[idx],
                            list = List<AnimeInfo?>(UiConst.MAX_BANNER_SIZE) { null },
                            sortClick = { _ -> }, animeClick = { id, idMal -> }
                        )
                    }
                }

                is Resource.Success -> {
                    val data = state.animeRecommendList.data
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
                                    onStartActivity(id, idMal)
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
                            sortClick = { route ->
                                onNavigate(route)
                            }, animeClick = { id, idMal ->
                                onStartActivity(id, idMal)
                            }
                        )
                    }
                }

                is Resource.Error -> {
                    item {
                        ItemErrorImage(message = state.errorMessage)
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemRecommendCategory(
    title: Pair<String, Triple<UiConst.SortType, Int?, String?>>,
    list: List<AnimeInfo?>,
    sortClick: (Screen.SortListScreen) -> Unit,
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
                sortClick(
                    Screen.SortListScreen(
                        sortOption = title.second.first.rawValue,
                        year = title.second.second,
                        season = title.second.third
                    )
                )
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