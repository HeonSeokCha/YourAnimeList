package com.chs.youranimelist.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            DataModule().module,
            DomainModule().module,
            PresentationModule().module
        )
    }
}