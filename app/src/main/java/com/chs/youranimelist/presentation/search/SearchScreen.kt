package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.chs.youranimelist.presentation.ui.theme.Purple200
import com.chs.youranimelist.util.Constant
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchScreen(
    searchKeyWord: String
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabList = listOf(
        "ANIME",
        "MANGA",
        "CHARACTER"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = Purple200
                )
            }
        ) {
            tabList.forEachIndexed { index, s ->
                Tab(
                    text = {
                        Text(
                            text = tabList[index],
                            maxLines = 1,
                            color = Purple200
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            count = tabList.size,
            state = pagerState,
            userScrollEnabled = false,
        ) { pager ->
            when (pager) {
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