import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.apollo)
    alias(libs.plugins.koin.compiler)
    alias(libs.plugins.konfig)
}

apollo {
    service("anilist") {
        packageName.set("com.chs.youranimelist.data")
        plugin("com.apollographql.cache:normalized-cache-apollo-compiler-plugin:1.0.9")
        pluginArgument("com.apollographql.cache.packageName", packageName.get())
    }
}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    android {
        namespace = "com.chs.youranimelist.shared"
        compileSdk = libs.versions.compileSdkVersion.get().toInt()
        minSdk = libs.versions.minSdkVersion.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        androidResources {
            enable = true
        }
    }

    listOf(
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
                implementation(libs.ktor.client.android)
                implementation(libs.koin.android)
                implementation(libs.koin.compose)
            }
        }

        commonMain {
            dependencies {
                implementation(libs.foundation)
                implementation(libs.components.resources)
                implementation(libs.ui.tooling.preview)
                implementation(libs.components.resources)

                implementation(libs.bundles.jetbrain)

                implementation(libs.kotlin.coroutine.core)
                implementation(libs.bundles.ktor)
                implementation(libs.kotlin.serialization)

                implementation(libs.bundles.apollo)

                implementation(libs.koin.core)
                implementation(libs.bundles.koin)
                api(libs.koin.annotation)
                implementation(libs.bundles.coil)
                implementation(libs.bundles.room)
                implementation(libs.bundles.store)

                implementation(libs.android.paging.common)
                implementation(libs.android.paging.compose)

                implementation(libs.htmlText)
                implementation(libs.dateTime)
            }
        }
    }
}

fun getApiKey(propertyKey: String): String {
    val properties = Properties()
    rootProject.file("local.properties").inputStream().use { properties.load(it) }
    return properties.getProperty(propertyKey)
}

buildkonfig {
    packageName = "com.chs.youranimelist"
    defaultConfigs {
        buildConfigField(STRING, "API_ACCESS_KEY", getApiKey("api.clientID"))
    }
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
    arg("KOIN_CONFIG_CHECK", "false")
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    listOf(
        "kspAndroid",
        "kspIosSimulatorArm64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.room.compiler)
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "com.chs.youranimelist.res"
    generateResClass = always
}

tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }.configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}