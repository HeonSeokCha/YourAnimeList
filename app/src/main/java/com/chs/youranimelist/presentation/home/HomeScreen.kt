package com.chs.youranimelist.presentation.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.presentation.common.ItemAnimeSmall
import com.chs.youranimelist.presentation.common.ItemAnimeSmallShimmer
import com.chs.youranimelist.presentation.shimmerEffect
import com.chs.youranimelist.util.Constant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navigator: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.animeRecommendList?.bannerList != null) {
            item {
                HorizontalPager(
                    modifier = if (state.isLoading) {
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .shimmerEffect()
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    },
                    state = pagerState,
                    pageCount = state.animeRecommendList?.bannerList!!.size
                ) { idx ->
                    ItemHomeBanner(
                        context = context,
                        banner = state.animeRecommendList?.bannerList!![idx]
                    )
                }
            }
        }

        if (state.animeRecommendList?.animeBasicList != null) {
            items(state.animeRecommendList?.animeBasicList!!.size) { idx ->
                ItemRecommendCategory(
                    Constant.HOME_SORT_TILE[idx],
                    state.animeRecommendList?.animeBasicList!![idx],
                    navigator,
                    context
                )
            }
        }

        if (state.isLoading) {
            items(Constant.HOME_SORT_TILE.size) { idx ->
                ItemSortShimmer(Constant.HOME_SORT_TILE[idx])
            }
        }
    }
}

@Composable
fun ItemRecommendCategory(
    title: String,
    list: List<AnimeInfo>,
    navigator: NavController,
    context: Context
) {
    Column {
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
                text = title,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    navigator.navigate("${Screen.SortListScreen.route}/$title")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
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
            items(list.size) {
                ItemAnimeSmall(
                    item = list[it],
                    onClick = {
                        context.startActivity(
                            Intent(
                                context, BrowseActivity::class.java
                            ).apply {
                                this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
                                this.putExtra(Constant.TARGET_ID, list[it].id)
                                this.putExtra(Constant.TARGET_ID_MAL, list[it].idMal)
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ItemSortShimmer(
    title: String
) {
    Column {
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
                text = title,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
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
                count = 6,
                itemContent = {
                    ItemAnimeSmallShimmer()
                }
            )
        }
    }
}