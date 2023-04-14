plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.common)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.androidX.paging.commons)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}