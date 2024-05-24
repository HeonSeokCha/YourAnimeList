package com.chs.presentation.home

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chs.common.Constants
import com.chs.common.Resource
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.main.Screen

@Composable
fun HomeScreen(
    state: HomeState,
    navController: NavHostController
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { UiConst.BANNER_SIZE }

    var showErrorItem by remember { mutableStateOf(false) }

    LaunchedEffect(state.isError) {
        if (state.isError != null) {
            showErrorItem = true
            Toast.makeText(context, state.isError, Toast.LENGTH_SHORT).show()
        }
    }

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
                                context.startActivity(
                                    Intent(
                                        context, BrowseActivity::class.java
                                    ).apply {
                                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                        this.putExtra(UiConst.TARGET_ID, id)
                                        this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                                    }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .height(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(data.bannerList.size) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
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
                            navController.navigate(route)
                        }, animeClick = { id, idMal ->
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                    this.putExtra(UiConst.TARGET_ID, id)
                                    this.putExtra(UiConst.TARGET_ID_MAL, idMal)
                                }
                            )
                        }
                    )
                }

            }

            is Resource.Error -> {
                item {
                    Text(
                        text = state.isError.toString()
                    )
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
                        year = title.second.second ?: 0,
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