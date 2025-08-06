package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.main.MainApp
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    val window = UIApplication.sharedApplication.keyWindow()
    val rootViewController = window?.rootViewController() as? UINavigationController

    MainApp {

        val secondViewController = BrowseViewController(
            controller = rootViewController!!,
            info = BrowseInfo(type = it.type, id = it.id, idMal = it.idMal)
        )

        rootViewController.pushViewController(
            viewController = secondViewController,
            animated = true
        )
    }
}