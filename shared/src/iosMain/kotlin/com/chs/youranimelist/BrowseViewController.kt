package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.browse.BrowseApp
import platform.UIKit.UINavigationController

fun BrowseViewController(
    controller: UINavigationController,
    info: BrowseInfo,
    onClose: () -> Unit
) = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    BrowseApp(
        browseInfo = info,
        onLinkClick = {},
        onClose = { controller.popViewControllerAnimated(true) }
    )
}
