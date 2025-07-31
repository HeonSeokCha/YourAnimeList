package com.chs.youranimelist

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.youranimelist.di.initKoin
import com.chs.youranimelist.presentation.main.MainApp

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    MainApp {

    }
}
