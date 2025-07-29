pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "home-library"

includeBuild("ok-lessons")
includeBuild("home-library-be")
includeBuild("build-plugin")