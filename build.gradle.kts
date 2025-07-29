plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "com.otus.otuskotlin.homelibrary"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}

tasks {
    create("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("home-library-be").task(":check"))
    }
}
