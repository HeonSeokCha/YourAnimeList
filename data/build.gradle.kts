plugins {
    kotlin("android")
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
    ksp {
        arg("KOIN_CONFIG_CHECK", "true")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    apollo {
        service("anilist") {
            packageName.set("com.chs.data")
        }
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.domain)
    implementation(libs.androidX.paging.compose)
    implementation(libs.kotlin.coroutine.android)
    implementation(libs.kotlin.serialization)

    implementation(libs.koin.compose)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.androidX.room.ktx)
    ksp(libs.androidX.room.compiler)

    implementation(libs.bundles.apollo)
    implementation(libs.bundles.ktor)

    testImplementation(libs.junit)
}