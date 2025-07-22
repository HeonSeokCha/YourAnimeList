package com.chs.youranimelist.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import presentation.UiConst
import presentation.ui.theme.Pink80

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel,
    onAnimeClick: (Int, Int) -> Unit,
    onCharaClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchEvent by viewModel.event.collectAsStateWithLifecycle(SearchEvent.Idle)
    val context = LocalContext.current

    LaunchedEffect(searchEvent) {
        when (searchEvent) {
            SearchEvent.OnError -> {
                Toast.makeText(context, "Something error in load Data..", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }

    SearchScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is SearchEvent.Click.Anime -> {
                    onAnimeClick(event.id, event.idMal)
                }

                is SearchEvent.Click.Chara -> {
                    onCharaClick(event.id)
                }

                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.selectedTabIdx) {
        pagerState.animateScrollToPage(state.selectedTabIdx)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(SearchEvent.Click.TabIdx(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SecondaryTabRow(state.selectedTabIdx) {
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
                        onEvent(SearchEvent.Click.TabIdx(index))
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
                    SearchAnimeScreen(pagingItem = state.searchAnimeResultPaging) {
                        onEvent(it)
                    }
                }

                1 -> {
                    SearchCharaScreen(pagingItems = state.searchCharaResultPaging) {
                        onEvent(it)
                    }
                }
            }
        }
    }
}