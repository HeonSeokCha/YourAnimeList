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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemPullToRefreshBox
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel,
    onAnimeClick: (Int, Int) -> Unit,
    onCharaClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is SearchEvent.OnAnimeClick -> {
                    onAnimeClick(event.id, event.idMal)
                }

                is SearchEvent.OnCharaClick -> {
                    onCharaClick(event.id)
                }

                SearchEvent.OnBackClick -> onBackClick()

                else -> Unit
            }
            viewModel.onEvent(event)
        }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

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
                UiConst.SEARCH_TAB_LIST.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Pink80
                            )
                        },
                        selected = state.selectedTabIdx == index,
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
                            searchQuery = state.query,
                            pagingItem = state.searchAnimeResultPaging
                        ) { item ->
                            onEvent(
                                SearchEvent.OnAnimeClick(
                                    id = item.id,
                                    idMal = item.idMal
                                )
                            )
                        }
                    }

                    1 -> {
                        SearchCharaScreen(
                            searchQuery = state.query,
                            pagingItems = state.searchCharaResultPaging
                        ) { charaId ->
                            onEvent(
                                SearchEvent.OnCharaClick(id = charaId)
                            )
                        }
                    }
                }
            }
        }
    }
}