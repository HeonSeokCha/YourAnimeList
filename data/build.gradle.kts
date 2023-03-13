@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    namespace = "com.chs.youranimelist.data"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    apollo {
        service("service") {
            packageName.set("com.chs")
        }
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidX.room.ktx)
    ksp(libs.androidX.room.compiler)
    implementation(libs.androidX.paging.compose)

    testImplementation(libs.junit)
}