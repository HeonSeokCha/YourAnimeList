package com.chs.presentation.charaList

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.UiConst
import com.chs.presentation.common.ItemCharaLarge
import com.chs.presentation.common.ItemNoResultImage

@Composable
fun CharaListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getYourCharaList()
    }

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
                    context.startActivity(
                        Intent(
                            context, BrowseActivity::class.java
                        ).apply {
                            this.putExtra(UiConst.TARGET_TYPE, UiConst.TARGET_CHARA)
                            this.putExtra(UiConst.TARGET_ID, charaInfo.id)
                        }
                    )
                }
            }
        }
    }
}