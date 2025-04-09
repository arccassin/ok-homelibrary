dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "ok-home-library"

includeBuild("ok-lessons")
includeBuild("ok-home-library-be")
includeBuild("build-plugin")