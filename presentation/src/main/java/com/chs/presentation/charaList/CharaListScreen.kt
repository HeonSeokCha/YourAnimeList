package com.chs.presentation.charaList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.presentation.common.ItemCharaLarge
import com.chs.presentation.common.ItemNoResultImage

@Composable
fun CharaListScreen(
    state: CharaListState,
    onActivityStart: (Int) -> Unit
) {

    if (state.charaList.isEmpty()) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                state.charaList,
                key = { it.id }
            ) { charaInfo ->
                ItemCharaLarge(character = charaInfo) {
                    onActivityStart(charaInfo.id)
                }
            }
        }
    }
}