pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "homelibrary"

//includeBuild("ok-lessons")
includeBuild("homelibrary-be")
includeBuild("homelibrary-libs")
includeBuild("backend-plugin")
includeBuild("homelibrary-other")
includeBuild("homelibrary-tests")