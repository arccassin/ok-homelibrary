plugins {
    alias(libs.plugins.jvm)
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.slf4j)
    implementation(libs.logback.classic)
    runtimeOnly(libs.kotlin.coroutines)
    testImplementation(kotlin("test"))
}