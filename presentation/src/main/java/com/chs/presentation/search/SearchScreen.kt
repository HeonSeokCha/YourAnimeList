package com.chs.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    state: SearchMediaState,
    searchQuery: String,
    onEvent: (SearchEvent) -> Unit,
    onBack: () -> Unit,
    onActivityStart: (String, Int, Int?) -> Unit
) {
    val pagerState = rememberPagerState { state.tabList.size }
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            onBack()
        }
    }


    LaunchedEffect(searchQuery) {
        snapshotFlow { searchQuery }
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .collect { onEvent(SearchEvent.ChangeSearchQuery(it)) }
    }

    ItemPullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                when (pagerState.currentPage) {
                    0 -> onEvent(SearchEvent.GetSearchAnimeResult)
                    1 -> onEvent(SearchEvent.GetSearchCharaResult)
                    else -> Unit
                }
                delay(500L)
                isRefreshing = false
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                state.tabList.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Pink80
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> {
                        SearchAnimeScreen(
                            searchQuery = searchQuery,
                            pagingItem = state.searchAnimeResultPaging
                        ) { item ->
                            onActivityStart(
                                UiConst.TARGET_MEDIA,
                                item.id,
                                item.idMal
                            )
                        }
                    }

                    1 -> {
                        SearchCharaScreen(
                            searchQuery = searchQuery,
                            pagingItems = state.searchCharaResultPaging
                        ) { item ->
                            onActivityStart(
                                UiConst.TARGET_CHARA,
                                item.id,
                                null
                            )
                        }
                    }
                }
            }
        }
    }
}