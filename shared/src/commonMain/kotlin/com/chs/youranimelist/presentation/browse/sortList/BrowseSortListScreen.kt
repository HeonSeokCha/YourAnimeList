package com.chs.youranimelist.presentation.browse.sortList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.youranimelist.presentation.common.GradientTopBar
import com.chs.youranimelist.presentation.sortList.SortedListScreenRoot
import com.chs.youranimelist.presentation.sortList.SortedViewModel

@Composable
fun BrowseSortedListScreen(
    viewModel: SortedViewModel,
    onClickAnime: (Int, Int) -> Unit,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        GradientTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            onCloseClick = onCloseClick
        )

        SortedListScreenRoot(
            viewModel = viewModel,
            onClickAnime = onClickAnime
        )
    }
}
