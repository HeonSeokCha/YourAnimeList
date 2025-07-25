package com.chs.youranimelist

import android.app.Application
import com.chs.youranimelist.di.initKoin
import org.koin.android.ext.koin.androidContext

class YourAnimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@YourAnimeApplication)
        }
    }
}