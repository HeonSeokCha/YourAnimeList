package com.chs.presentation.search

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.PullToRefreshBox
import com.chs.presentation.ui.theme.Pink80
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    state: SearchMediaState,
    onEvent: (SearchEvent) -> Unit,
    onBack: () -> Unit,
) {
    val pagerState = rememberPagerState { state.tabList.size }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            onBack()
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            when (pagerState.currentPage) {
                0 -> onEvent(SearchEvent.GetSearchAnimeResult)
                1 -> onEvent(SearchEvent.GetSearchCharaResult)
                else -> Unit
            }
            isRefreshing = false
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
                        SearchAnimeScreen(pagingItem = state.searchAnimeResultPaging) { item ->
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                    putExtra(UiConst.TARGET_ID, item.id)
                                    putExtra(UiConst.TARGET_ID_MAL, item.idMal)
                                }
                            )
                        }
                    }

                    1 -> {
                        SearchCharaScreen(pagingItems = state.searchCharaResultPaging) { item ->
                            context.startActivity(
                                Intent(
                                    context, BrowseActivity::class.java
                                ).apply {
                                    this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                                    this.putExtra(UiConst.TARGET_ID, item.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}