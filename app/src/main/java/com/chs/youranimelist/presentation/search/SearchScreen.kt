package com.chs.youranimelist.presentation.search

import android.util.Log
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
import androidx.compose.ui.text.style.TextOverflow
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
    Log.e("Recomposition", searchKeyWord)
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
            tabList.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
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
        LaunchedEffect(pagerState) {
            snapshotFlow {
                pagerState.currentPage
            }.collect { page ->
                Log.e("page", page.toString())
            }
        }
        HorizontalPager(
            count = tabList.size,
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