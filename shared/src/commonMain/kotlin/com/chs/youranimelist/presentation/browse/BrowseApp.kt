package com.chs.youranimelist.presentation.browse

import androidx.compose.runtime.Composable
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme

@Composable
fun BrowseApp(
    browseInfo: BrowseInfo,
    onLinkClick: (String) -> Unit,
    onClose: () -> Unit
) {
    YourAnimeListTheme {
        BrowseNavHost(
            browseInfo = browseInfo,
            onLinkClick = onLinkClick,
            onClose = onClose
        )
    }
}