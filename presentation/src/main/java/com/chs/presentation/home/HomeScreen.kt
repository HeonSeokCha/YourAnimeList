package com.chs.presentation.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemAnimeSmall
import com.chs.presentation.main.Screen
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pagerState = rememberPagerState {
        state.animeRecommendList?.bannerList?.size ?: 1
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { idx ->
                ItemHomeBanner(
                    banner = if (state.animeRecommendList?.bannerList.isNullOrEmpty()) {
                        null
                    } else state.animeRecommendList!!.bannerList[idx]
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

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(state.animeRecommendList?.bannerList?.size ?: 0) { iteration ->
                    val color =
                        if (pagerState.currentPage % state.animeRecommendList?.bannerList?.size!! == iteration) Color.DarkGray
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
            state.animeRecommendList?.animeBasicList?.size ?: UiConst.MAX_BANNER_SIZE,
            key = { state.animeCategoryList[it].first }
        ) { idx ->
            ItemRecommendCategory(
                title = state.animeCategoryList[idx],
                list = state.animeRecommendList?.animeBasicList?.get(idx)
                    ?: List<AnimeInfo?>(UiConst.MAX_BANNER_SIZE) { null },
                navigator = navController
            )
        }
    }
}

@Composable
fun ItemRecommendCategory(
    title: Pair<String, Triple<UiConst.SortType, Int?, String?>>,
    list: List<AnimeInfo?>,
    navigator: NavController,
) {
    val context: Context = LocalContext.current
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
                text = title.first,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    navigator.navigate(
                        "${Screen.SortListScreen.route}/${title.second.first.rawValue}/${title.second.second}/${title.second.third}"
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
                list.size,
            ) {
                ItemAnimeSmall(
                    item = list[it],
                    onClick = {
                        if (list[it] != null) {
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                    this.putExtra(UiConst.TARGET_ID, list[it]!!.id)
                                    this.putExtra(UiConst.TARGET_ID_MAL, list[it]!!.idMal)
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}