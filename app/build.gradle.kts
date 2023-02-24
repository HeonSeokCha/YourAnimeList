plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
    id("com.apollographql.apollo3") version "3.7.4"
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
    compileSdk = 33

    defaultConfig {
        applicationId = "com.chs.youranimelist"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.chs.youranimelist.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.4.0-beta01")
    implementation("androidx.compose.material3:material3:1.1.0-alpha06")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.3")


    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.navigation:navigation-testing:2.5.3")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.45")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.45")

    //Ktor
    implementation("io.ktor:ktor-client-core:2.2.3")
    implementation("io.ktor:ktor-client-android:2.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.3")
    implementation("io.ktor:ktor-client-logging:2.2.3")

    //Room
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    //Apollo
    implementation("com.apollographql.apollo3:apollo-runtime:3.7.4")
    implementation("com.apollographql.apollo3:apollo-api:3.7.4")
    implementation("com.apollographql.apollo3:apollo-normalized-cache:3.7.4")

    // Paging
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // CustomTabs
    implementation("androidx.browser:browser:1.5.0")
}
