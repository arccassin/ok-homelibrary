dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../backend-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

// Включает вот такую конструкцию
//implementation(projects.m2l5Gradle.sub1.ssub1)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":homelibrary-api-log")
include(":homelibrary-api-v1-kmp")
include(":homelibrary-common")
include(":homelibrary-app-common")
include(":homelibrary-stubs")
include(":homelibrary-biz")
include(":homelibrary-app-ktor")
include(":homelibrary-app-kafka")