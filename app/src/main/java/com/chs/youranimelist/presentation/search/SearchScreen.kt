package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.chs.youranimelist.presentation.ui.theme.Pink80
import com.chs.youranimelist.util.Constant
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    searchKeyWord: String,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    val tabList = listOf(
        "ANIME",
        "MANGA",
        "CHARACTER"
    )

    DisposableEffect(Unit) {
        onDispose {
            onBack()
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
                TabRowDefaults.Indicator(
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
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(
            pageCount = tabList.size,
            state = pagerState,
            userScrollEnabled = false,
        ) {
            when (pagerState.currentPage) {
                0 -> {
                    SearchMediaScreen(
                        searchType = Constant.TARGET_ANIME,
                        searchKeyWord = searchKeyWord
                    )
                }
                1 -> {
                    SearchMediaScreen(
                        searchType = Constant.TARGET_MANGA,
                        searchKeyWord = searchKeyWord
                    )
                }
                2 -> {
                    SearchMediaScreen(
                        searchType = Constant.TARGET_CHARA,
                        searchKeyWord = searchKeyWord
                    )
                }
            }
        }
    }
}