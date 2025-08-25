plugins {
    id("build-jvm")
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(projects.homelibraryCommon)
    api(projects.homelibraryRepoCommon)

    implementation(libs.coroutines.core)
    implementation(libs.uuid)

    implementation(libs.db.postgres)
//  implementation(libs.db.hikari)
    implementation(libs.bundles.exposed)

//    testImplementation(kotlin("test-junit"))
    testImplementation(projects.homelibraryRepoTests)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.logback)
    testImplementation(libs.bundles.kotest)

}
