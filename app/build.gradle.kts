@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.apollo)
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
    namespace = "com.chs.youranimelist"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "com.chs.youranimelist"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "com.chs.youranimelist.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    apollo {
        service("service") {
            packageName.set("com.chs")
        }
    }
}

dependencies {
    implementation(projects.presentation)
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

//    implementation(libs.androidX.core)
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
//
//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.compose.ui:ui:1.4.0-beta02")
//    implementation("androidx.compose.material3:material3:1.1.0-alpha07")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-beta02")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
//    implementation("androidx.activity:activity-compose:1.6.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("com.google.truth:truth:1.1.3")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")
//
//
//    // Compose dependencies
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
//    implementation("androidx.compose.material:material-icons-extended:1.3.1")
//    implementation("androidx.activity:activity-compose:1.6.1")
//    implementation("androidx.navigation:navigation-compose:2.5.3")
//
//    //Dagger - Hilt
//    implementation("com.google.dagger:hilt-android:2.45")
//    kapt("com.google.dagger:hilt-android-compiler:2.45")
//    kapt("androidx.hilt:hilt-compiler:1.0.0")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}
