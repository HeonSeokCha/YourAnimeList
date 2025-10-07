package com.chs.youranimelist.presentation.bottom.charaList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.domain.model.MediaType
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.common.ItemCharaLarge
import com.chs.youranimelist.presentation.common.ItemNoResultImage

@Composable
fun CharaListScreenRoot(
    viewModel: CharacterListViewModel,
    onNavigateCharaDetail: (BrowseInfo) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CharaListEffect.NavigateCharaDetail -> {
                    onNavigateCharaDetail(BrowseInfo(MediaType.CHARACTER, id = effect.id))
                }
            }
        }
    }

    CharaListScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}


@Composable
fun CharaListScreen(
    state: CharaListState,
    onIntent: (CharaListIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when {
            state.isLoading -> {
                items(UiConst.BANNER_SIZE) {
                    ItemCharaLarge()
                }
            }

            state.isEmpty -> {
                item {
                    ItemNoResultImage(modifier = Modifier.fillParentMaxSize())
                }
            }

            else -> {
                items(
                    items = state.list,
                    key = { it.id }
                ) { charaInfo ->
                    ItemCharaLarge(character = charaInfo) {
                        onIntent(CharaListIntent.ClickChara(charaInfo.id))
                    }
                }
            }
        }
    }
}