package com.chs.presentation.search

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.common.ItemCharaLarge
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchCharaScreen(
    pagingItems: Flow<PagingData<CharacterInfo>>?
) {

    val context = LocalContext.current
    val lazyColScrollState = rememberLazyListState()
    val charaItems = pagingItems?.collectAsLazyPagingItems()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColScrollState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (charaItems != null) {
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



            when (charaItems.loadState.refresh) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemCharaLarge(character = null) { }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }

            when (charaItems.loadState.append) {
                is LoadState.Loading -> {
                    items(10) {
                        ItemCharaLarge(character = null) { }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Something Wrong for Loading List."
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}