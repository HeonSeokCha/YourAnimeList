package com.chs.youranimelist

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CompletionHandlerException
import kotlinx.coroutines.InternalCoroutinesApi

class YourAnimeApplication : Application() {

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            if (throwable is CompletionHandlerException &&
                throwable.stackTrace.any { it.className.contains("coil3.compose.AsyncImagePainter") }
            ) {
                Log.e("Coil", "Known Coil AsyncImagePainter cancellation race, ignoring", throwable)
                return@setDefaultUncaughtExceptionHandler
            }
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }
}