@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
//    id("com.google.dagger.hilt.android")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.chs.youranimelist.data"
}

dependencies {
    implementation(libs.kotlin.coroutine.core)
}