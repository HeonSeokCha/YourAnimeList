plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(projects.common)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.androidX.paging.commons)
    testImplementation(libs.junit)
}