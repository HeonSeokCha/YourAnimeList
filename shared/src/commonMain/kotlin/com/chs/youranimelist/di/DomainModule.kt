package com.chs.youranimelist.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class])
@ComponentScan("com.chs.youranimelist.domain")
class DomainModule