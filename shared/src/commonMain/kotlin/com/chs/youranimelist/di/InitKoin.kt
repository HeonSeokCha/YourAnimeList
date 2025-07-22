package com.chs.youranimelist.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            RemoteModule().module,
            LocalModule().module,
            RepositoryModule().module,
            UseCaseModule().module,
            defaultModule
        )
    }
}