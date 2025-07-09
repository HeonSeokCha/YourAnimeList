package com.chs.youranimelist

import android.app.Application
import com.chs.data.module.LocalModule
import com.chs.data.module.RemoteModule
import com.chs.data.module.RepositoryModule
import com.chs.data.module.provideRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class YourAnimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@YourAnimeApplication)
            modules(
                provideRepositoryModule,
                RemoteModule().module,
                LocalModule().module,
                RepositoryModule().module,
                defaultModule
            )
        }
    }
}