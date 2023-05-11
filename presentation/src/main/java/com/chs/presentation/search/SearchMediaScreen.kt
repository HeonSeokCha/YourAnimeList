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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.chs.presentation.LoadingIndicator
import com.chs.presentation.browse.BrowseActivity
import com.chs.common.UiConst
import com.chs.domain.model.AnimeInfo
import com.chs.domain.model.CharacterInfo

@Composable
fun SearchMediaScreen(
    searchType: String,
    searchKeyWord: String,
    viewModel: SearchMediaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()

    viewModel.searchPage = searchType

    LaunchedEffect(searchKeyWord) {
        if (searchKeyWord.isNotEmpty()) {
            viewModel.search(searchKeyWord)
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
                pagingItems?.let {
                    val animeItems = pagingItems as LazyPagingItems<AnimeInfo>
                    items(
                        animeItems,
                        key = { it.id }
                    ) { item ->
                        if (item != null) {
                            SearchMediaItem(item) {
                                context.startActivity(
                                    Intent(
                                        context, BrowseActivity::class.java
                                    ).apply {
                                        this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_MEDIA)
                                        this.putExtra(UiConst.TARGET_ID, item.id)
                                        this.putExtra(UiConst.TARGET_ID_MAL, item.idMal)
                                    }
                                )
                            }
                        }

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
                pagingItems?.let {
                    val charaItems = pagingItems as LazyPagingItems<CharacterInfo>
                    items(
                        charaItems,
                        key = { it.id }
                    ) { item ->
                        if (item != null) {
                            SearchMediaItem(item) {
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

    when (pagingItems?.loadState?.source?.refresh) {
        is LoadState.Loading -> {
            LoadingIndicator()
        }

        is LoadState.Error -> {
            Toast.makeText(context, "An error occurred while loading...", Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }
}