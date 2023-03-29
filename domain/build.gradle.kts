plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(projects.common)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.androidX.paging.commons)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}