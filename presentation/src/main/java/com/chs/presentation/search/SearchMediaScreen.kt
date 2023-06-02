package com.chs.presentation.search

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()
    var placeItemShow by remember { mutableStateOf(false) }

    LaunchedEffect(context, viewModel) {
        viewModel.initSearchType(searchType)
    }

    LaunchedEffect(searchKeyWord) {
        if (searchKeyWord.isNotEmpty()) {
            viewModel.clear()
            viewModel.search(searchKeyWord)
            lazyColScrollState.scrollToItem(0, 0)
        }
    }

    val pagingItems = when (searchType) {
        UiConst.TARGET_ANIME -> {
            state.searchAnimeResultPaging?.collectAsLazyPagingItems()
        }

//        UiConst.TARGET_MANGA -> {
//            state.searchMangaResultPaging?.collectAsLazyPagingItems()
//        }

        UiConst.TARGET_CHARA -> {
            state.searchCharaResultPaging?.collectAsLazyPagingItems()
        }

        else -> {
            state.searchAnimeResultPaging?.collectAsLazyPagingItems()
        }
    }

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
                        key = animeItems.itemKey(key = { it.id }),
                        contentType = animeItems.itemContentType()
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

//            UiConst.TARGET_MANGA -> {
//                pagingItems?.let {
//                    val mangaItems = pagingItems as LazyPagingItems<SearchMangaQuery.Medium>
//                    items(mangaItems) { item ->
//                        SearchMediaItem(item?.animeList!!) {
//                            context.startActivity(
//                                Intent(
//                                    context, BrowseActivity::class.java
//                                ).apply {
//                                    this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_MEDIA)
//                                    this.putExtra(
//                                        Constant.TARGET_ID,
//                                        item.animeList.id
//                                    )
//                                    this.putExtra(
//                                        Constant.TARGET_ID_MAL,
//                                        item.animeList.idMal
//                                    )
//                                }
//                            )
//                        }
//                    }
//                }
//            }

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

            else -> false
        }

        placeItemShow = when (pagingItems.loadState.source.append) {
            is LoadState.Loading -> {
                true
            }

            is LoadState.Error -> {
                Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT)
                    .show()
                pagingItems.itemCount <= 0
            }

            else -> pagingItems.itemCount <= 0
        }
    }
}