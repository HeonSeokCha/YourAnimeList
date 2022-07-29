package com.chs.youranimelist.presentation.charaList

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
import com.chs.youranimelist.presentation.browse.BrowseActivity
import com.chs.youranimelist.util.Constant

@Composable
fun CharaListScreen(
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.getYourCharaList()
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