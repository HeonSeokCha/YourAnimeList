package com.chs.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.presentation.ui.theme.Pink80
import com.chs.presentation.UiConst
import com.chs.presentation.common.LoadingIndicator
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    searchQuery: String,
    onBack: () -> Unit,
) {
    val tabList = listOf(
        "ANIME",
        "CHARACTER"
    )
    val pagerState = rememberPagerState(initialPage = 0) { tabList.size }
    val coroutineScope = rememberCoroutineScope()

    val viewModel: SearchMediaViewModel = hiltViewModel()

    DisposableEffect(Unit) {
        onDispose {
            onBack()
        }
    }

    LaunchedEffect(searchQuery) {
        snapshotFlow { searchQuery }
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .collect {
                viewModel.search(it)
            }
    }

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
            tabList.forEachIndexed { index, title ->
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
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    SearchAnimeScreen(pagingItem = viewModel.state.searchAnimeResultPaging)
                }

                1 -> {
                    SearchCharaScreen(pagingItems = viewModel.state.searchCharaResultPaging)
                }
            }
        }
    }
}