package com.chs.youranimelist

import com.chs.youranimelist.di.localModule
import com.chs.youranimelist.di.platformModule
import com.chs.youranimelist.di.remoteModule
import com.chs.youranimelist.di.repositoryModule
import com.chs.youranimelist.di.useCaseModule
import com.chs.youranimelist.di.viewModelModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            localModule,
            remoteModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}
