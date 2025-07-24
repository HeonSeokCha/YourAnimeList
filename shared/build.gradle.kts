import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.apollo)
}

apollo {
    service("anilist") {
        packageName.set("com.chs.youranimelist.data")
    }
}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        androidMain {
            dependencies {
                implementation(libs.androidX.compose.ui.tooling.preview)
                implementation(libs.androidX.activity.compose)
                implementation(libs.ktor.client.android)
                implementation(libs.androidX.navigation.compose)

                implementation(libs.koin.android)
                implementation(libs.koin.compose)
                implementation(libs.room.runtime.android)
            }
        }

        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.jetbrain.lifecycle.runtime.compose)
                implementation(libs.jetbrain.lifecycle.viewmodel.compose)
                implementation(libs.jetbrain.navigation.compose)
                implementation(libs.kotlin.coroutine.core)

                implementation(libs.bundles.ktor)
                implementation(libs.kotlin.serialization)

                implementation(libs.bundles.apollo)

                implementation(libs.koin.core)
                api(libs.koin.annotations)

                implementation(libs.bundles.koin)
                implementation(libs.bundles.coil)
                implementation(libs.bundles.room)

                implementation(libs.cashapp.paging.common)
                implementation(libs.cashapp.paging.compose)
            }
        }
    }
}

android {
    namespace = "com.chs.youranimelist"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.chs.youranimelist"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {}
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
    arg("KOIN_CONFIG_CHECK", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    listOf(
        "kspAndroid",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.room.compiler)
        add(it, libs.koin.ksp.compiler)
    }
}

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}