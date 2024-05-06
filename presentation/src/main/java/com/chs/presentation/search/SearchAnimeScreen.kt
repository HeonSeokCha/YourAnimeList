package com.chs.presentation.search

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemAnimeLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchAnimeScreen(
    pagingItem: Flow<PagingData<AnimeInfo>>?
) {
    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()
//    var placeItemShow by remember { mutableStateOf(false) }
    val animeItems = pagingItem?.collectAsLazyPagingItems()


    if (animeItems != null && animeItems.itemCount == 0) {
        ItemNoResultImage()
    }

    if (animeItems != null && animeItems.itemCount != 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyColScrollState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
    }
}