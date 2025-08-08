plugins {
    application
    id("build-jvm")
    alias(libs.plugins.muschko.java)
}

application {
    mainClass.set("com.otus.otuskotlin.homelibrary.app.kafka.MainKt")
}

docker {
    javaApplication {
        baseImage.set("openjdk:17.0.2-slim")
    }
}

dependencies {
    implementation(libs.kafka.client)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.atomicfu)

    implementation("com.otus.otuskotlin.homelibrary.libs:homelibrary-lib-logging-logback")

    implementation(project(":homelibrary-app-common"))

    // transport models
    implementation(project(":homelibrary-common"))
    implementation(project(":homelibrary-api-v1-kmp"))
    // logic
    implementation(project(":homelibrary-biz"))

    testImplementation(kotlin("test-junit"))
}
