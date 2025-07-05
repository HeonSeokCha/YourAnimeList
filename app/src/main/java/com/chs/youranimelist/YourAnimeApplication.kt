package com.chs.youranimelist

import android.app.Application
import com.chs.data.module.provideLocalModule
import com.chs.data.module.provideRemoteModule
import com.chs.data.module.provideRepositoryModule
import com.chs.presentation.providePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class YourAnimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@YourAnimeApplication)
            modules(
                provideRepositoryModule,
                provideRemoteModule,
                provideLocalModule,
                providePresentationModule
            )
        }
    }
}