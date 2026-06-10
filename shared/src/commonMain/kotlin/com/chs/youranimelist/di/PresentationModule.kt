package com.chs.youranimelist.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DomainModule::class])
@ComponentScan("com.chs.youranimelist.presentation")
class PresentationModule