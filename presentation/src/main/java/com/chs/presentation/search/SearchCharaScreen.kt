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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemCharaLarge
import com.chs.presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchCharaScreen(
    pagingItems: Flow<PagingData<CharacterInfo>>?
) {

    val context = LocalContext.current
//    var placeItemShow by remember { mutableStateOf(false) }
//    var isEmptyShow by remember { mutableStateOf(false) }
    val lazyColScrollState = rememberLazyListState()
    val charaItems = pagingItems?.collectAsLazyPagingItems()

    if (charaItems != null && charaItems.itemCount == 0) {
        ItemNoResultImage()
    }

    if (charaItems != null && charaItems.itemCount != 0) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyColScrollState,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                count = charaItems.itemCount,
                key = charaItems.itemKey(key = { it.id })
            ) { idx ->
                val item = charaItems[idx]
                ItemCharaLarge(item) {
                    if (item != null) {
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