package com.chs.presentation.charaList

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chs.presentation.browse.BrowseActivity
import com.chs.presentation.util.Constant

@Composable
fun CharaListScreen(
    searchQuery: String,
    viewModel: CharacterListViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getYourCharaList()
    }

    LaunchedEffect(searchQuery) {
        viewModel.getSearchResultChara(searchQuery)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(state.charaList.size) { idx ->
            ItemYourChara(character = state.charaList[idx]) {
                context.startActivity(
                    Intent(
                        context, BrowseActivity::class.java
                    ).apply {
                        this.putExtra(Constant.TARGET_TYPE, Constant.TARGET_CHARA)
                        this.putExtra(Constant.TARGET_ID, state.charaList[idx].charaId)
                    }
                )
            }
        }
    }
}