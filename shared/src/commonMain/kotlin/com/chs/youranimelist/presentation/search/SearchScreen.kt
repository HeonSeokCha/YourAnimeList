package com.chs.youranimelist.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.chs.youranimelist.domain.model.AnimeInfo
import com.chs.youranimelist.domain.model.CharacterInfo
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.ui.theme.Pink80
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel,
    onAnimeClick: (Int, Int) -> Unit,
    onCharaClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateAnimeDetail -> onAnimeClick(effect.id, effect.idMal)
                is SearchEffect.NavigateCharaDetail -> onCharaClick(effect.id)
                SearchEffect.ShowErrorSnackBar -> {

                }
            }
        }
    }

    SearchScreen(
        state = state,
        animePaging = viewModel.animePaging,
        charaPaging = viewModel.charaPaging,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    animePaging: Flow<PagingData<AnimeInfo>>,
    charaPaging: Flow<PagingData<CharacterInfo>>,
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.selectedTabIdx) {
        pagerState.animateScrollToPage(state.selectedTabIdx)
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
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            key = { it }
        ) { page ->
            when (page) {
                0 -> {
                    SearchAnimeScreen(
                        state = state,
                        pagingItem = animePaging,
                        onIntent = onIntent
                    )
                }

                1 -> {
                    SearchCharaScreen(
                        state = state,
                        pagingItem = charaPaging,
                        onIntent = onIntent
                    )
                }
            }
        }
    }
}