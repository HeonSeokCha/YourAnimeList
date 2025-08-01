package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.di.initKoin
import com.chs.youranimelist.domain.model.BrowseInfo
import com.chs.youranimelist.presentation.main.MainApp
import platform.UIKit.UIApplication

fun MainViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {
    val window = UIApplication.sharedApplication.keyWindow()
    val rootViewController = window?.rootViewController()

    MainApp {

        val secondViewController = BrowseViewController(
            controller = rootViewController!!,
            info = BrowseInfo(type = it.type, id = it.id, idMal = it.idMal)
        )

        rootViewController.presentViewController(
            secondViewController,
            animated = false,
            completion = null
        )
    }
}