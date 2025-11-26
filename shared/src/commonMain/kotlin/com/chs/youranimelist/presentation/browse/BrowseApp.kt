package com.chs.youranimelist.presentation.browse

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.main.Screen
import com.chs.youranimelist.presentation.ui.theme.YourAnimeListTheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

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