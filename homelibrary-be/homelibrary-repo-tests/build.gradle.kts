plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                implementation(projects.homelibraryCommon)
                implementation(projects.homelibraryRepoCommon)
                implementation(projects.homelibraryStubs)

            }
        }
        commonTest {
            dependencies {
                implementation(projects.homelibraryStubs)
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}

tasks {
    register("test") {
        group = "build"
        gradle.includedBuilds.forEach {
            dependsOn(it.task(":test"))
        }
    }
}