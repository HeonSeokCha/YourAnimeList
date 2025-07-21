package presentation.browse.anime

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.chs.domain.model.AnimeInfo
import presentation.common.ItemAnimeLarge
import presentation.common.ItemNoResultImage
import kotlinx.coroutines.flow.Flow

@Composable
fun AnimeRecScreen(
    animeRecList: Flow<PagingData<AnimeInfo>>,
    onNavigate: (Int, Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val lazyPagingItems = animeRecList.collectAsLazyPagingItems()
    var isLoading by remember { mutableStateOf(false) }
    var isEmpty by remember { mutableStateOf(false) }
    var isAppending by remember { mutableStateOf(false) }
    val context = LocalContext.current

    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> isLoading = true

        is LoadState.Error -> {
            Toast.makeText(context, "Something error in load Data..", Toast.LENGTH_SHORT).show()
            isLoading = false
        }

        else -> {
            isLoading = false
            isEmpty = lazyPagingItems.itemCount == 0
        }
    }

    isAppending = when (lazyPagingItems.loadState.append) {
        is LoadState.Loading -> true

        else -> false
    }

    if (isEmpty) {
        ItemNoResultImage()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            if (isLoading) {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            }

            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey(key = { it.id })
            ) { index ->
                val item = lazyPagingItems[index]
                ItemAnimeLarge(item) {
                    if (item != null) {
                        onNavigate(
                            item.id,
                            item.idMal
                        )
                    }
                }
            }

            if (isAppending) {
                items(10) {
                    ItemAnimeLarge(null) {}
                }
            }
        }
    }
}