@file:Suppress("UnstableApiUsage", "UNCHECKED_CAST")

include(":common")


enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YourAnimeList"

settings.include(":app")
settings.include(":data")
settings.include(":presentation")
settings.include(":domain")
