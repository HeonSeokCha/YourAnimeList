package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.browse.BrowseApp
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController

fun BrowseViewController(
    controller: UINavigationController,
    info: BrowseInfo,
) = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    BrowseApp(
        browseInfo = info,
        onLinkClick = { url ->
            val application = UIApplication.sharedApplication
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl == null) return@BrowseApp

            if (!application.canOpenURL(nsUrl)) return@BrowseApp

            if (application.canOpenURL(nsUrl)) {
                application.openURL(
                    url = nsUrl,
                    options = mapOf<Any?, Any>(),
                    completionHandler = { success ->
                        if (!success) {
                            println("Failed to open URL: $url")
                        }
                    }
                )
            } else {
                println("Cannot open URL scheme: $url")
            }
        },
        onClose = { controller.popViewControllerAnimated(true) }
    )
}
