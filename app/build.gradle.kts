plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    id("com.apollographql.apollo3") version "3.7.3"
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
    compileSdk =  33

    defaultConfig {
        applicationId = "com.chs.youranimelist"
        minSdk  = 29
        targetSdk  = 33
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
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    apollo {
        service("service") {
            packageName.set("com.chs.youranimelist")
            generateOptionalOperationVariables.set(false)
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.3.2")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.2")


    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.navigation:navigation-testing:2.5.3")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44.2")

    //Ktor
    implementation("io.ktor:ktor-client-core:2.2.2")
    implementation("io.ktor:ktor-client-android:2.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.2")
    implementation("io.ktor:ktor-client-logging:2.2.2")

    //Room
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    //Apollo
    implementation("com.apollographql.apollo3:apollo-runtime:3.7.3")
    implementation("com.apollographql.apollo3:apollo-api:3.7.3")
    implementation("com.apollographql.apollo3:apollo-normalized-cache:3.7.3")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")

    // Pager
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    // Paging
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")

    // Flowlayout
    implementation("com.google.accompanist:accompanist-flowlayout:0.28.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // CustomTabs
    implementation("androidx.browser:browser:1.4.0")
}
