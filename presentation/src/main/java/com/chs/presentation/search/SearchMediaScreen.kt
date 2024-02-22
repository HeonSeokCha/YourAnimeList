package com.chs.presentation.search

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemCharaLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel(key = searchType)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()
    var placeItemShow by remember { mutableStateOf(false) }
    var isEmptyShow by remember { mutableStateOf(false) }

    LaunchedEffect(context, viewModel) {
        viewModel.initSearchType(searchType)
    }

    LaunchedEffect(searchKeyWord) {
        snapshotFlow { searchKeyWord }
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .collect {
                viewModel.search(it)
                lazyColScrollState.scrollToItem(0, 0)
            }
    }

    val pagingItems = when (searchType) {
        UiConst.TARGET_ANIME -> {
            state.searchAnimeResultPaging?.collectAsLazyPagingItems()
        }

        UiConst.TARGET_CHARA -> {
            state.searchCharaResultPaging?.collectAsLazyPagingItems()
        }

        else -> {
            state.searchAnimeResultPaging?.collectAsLazyPagingItems()
        }
    }

    if (isEmptyShow) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyColScrollState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (searchType) {
                UiConst.TARGET_ANIME -> {
                    val animeItems = pagingItems as LazyPagingItems<AnimeInfo>?
                    if (animeItems != null) {
                        items(
                            count = animeItems.itemCount,
                            key = animeItems.itemKey(key = { it.id })
                        ) { index ->
                            val item = animeItems[index]
                            ItemAnimeLarge(anime = item) {
                                if (item != null) {
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
                        }
                    }

                    if (placeItemShow) {
                        items(6) {
                            ItemAnimeLarge(anime = null) { }
                        }
                    }
                }

                UiConst.TARGET_CHARA -> {
                    val charaItems = pagingItems as LazyPagingItems<CharacterInfo>?
                    if (charaItems != null) {
                        items(
                            count = charaItems.itemCount,
                            key = charaItems.itemKey(key = { it.id }),
                            contentType = charaItems.itemContentType()
                        ) { idx ->
                            val item = charaItems[idx]
                            ItemCharaLarge(item) {
                                context.startActivity(
                                    Intent(
                                        context, BrowseActivity::class.java
                                    ).apply {
                                        if (item != null) {
                                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                                            this.putExtra(UiConst.TARGET_ID, item.id)
                                        }
                                    }
                                )
                            }
                        }

                        if (placeItemShow) {
                            items(6) {
                                ItemCharaLarge(null) { }
                            }
                        }
                    }
                }
            }
        }
    }



    if (pagingItems != null) {
        placeItemShow = when (pagingItems.loadState.source.refresh) {
            is LoadState.Loading -> {
                true
            }

            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            else -> {
                isEmptyShow = pagingItems.itemCount == 0
                pagingItems.itemCount < 0
            }
        }
    }
}