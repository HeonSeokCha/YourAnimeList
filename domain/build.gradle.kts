plugins {
    id("com.android.library")
}

android {
    namespace = "com.chs.youranimelist.domain"
}

dependencies {
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.dagger)
}