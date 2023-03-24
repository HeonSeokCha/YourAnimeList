@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    apollo {
        service("service") {
            packageName.set("com.chs")
        }
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.domain)
    implementation(libs.androidX.paging.compose)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidX.room.ktx)
    ksp(libs.androidX.room.compiler)

    implementation(libs.bundles.apollo)
    implementation(libs.bundles.ktor)

    testImplementation(libs.junit)
}