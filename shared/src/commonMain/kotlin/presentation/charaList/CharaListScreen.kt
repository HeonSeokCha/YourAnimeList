package presentation.charaList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.common.ItemCharaLarge
import presentation.common.ItemNoResultImage

@Composable
fun CharaListScreen(
    state: CharaListState,
    onActivityStart: (Int) -> Unit
) {
    if (!state.isLoading && state.list.isEmpty()) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (state.isLoading) {
                items(10) {
                    ItemCharaLarge(null) { }
                }
            } else {
                items(
                    items = state.list,
                    key = { it.id }
                ) { charaInfo ->
                    ItemCharaLarge(character = charaInfo) {
                        onActivityStart(charaInfo.id)
                    }
                }
            }
        }
    }
}