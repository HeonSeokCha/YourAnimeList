package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.browse.BrowseApp
import platform.UIKit.UIViewController

fun BrowseViewController(
    controller: UIViewController,
    info: BrowseInfo
) = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    BrowseApp(
        browseInfo = info,
        onLinkClick = {},
        onClose = {
            controller.dismissViewControllerAnimated(true) {}
        }
    )
}
