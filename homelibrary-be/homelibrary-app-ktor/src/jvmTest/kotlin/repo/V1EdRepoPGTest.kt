package repo

import com.otus.otuskotlin.homelibrary.api.v1.models.EdRequestDebugMode
import com.otus.otuskotlin.homelibrary.app.ktor.HmlrAppSettings
import com.otus.otuskotlin.homelibrary.app.ktor.repo.EdRepoPGTest
import com.otus.otuskotlin.homelibrary.app.ktor.repo.V1EdRepoBaseTest
import com.otus.otuskotlin.homelibrary.backend.repo.postgresql.RepoEdSql
import com.otus.otuskotlin.homelibrary.common.HmlrCorSettings
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import kotlin.test.BeforeTest
import kotlin.test.Ignore

@Ignore
open class V1EdRepoPGTest() : V1EdRepoBaseTest() {
    override val workMode = EdRequestDebugMode.TEST

    private fun grbAppSettings(repo: IRepoEd) = HmlrAppSettings(
        corSettings = HmlrCorSettings(
            repoTest = repo,
            repoProd = repo,
        )
    )

    override val appSettingsCreate: HmlrAppSettings by lazy {
        grbAppSettings(
            repo = EdRepoPGTest.repoUnderTestContainer(
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsRead: HmlrAppSettings by lazy {
        grbAppSettings(
            repo = EdRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initEd),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsUpdate: HmlrAppSettings by lazy {
        grbAppSettings(
            repo = EdRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initEd),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsDelete: HmlrAppSettings by lazy {
        grbAppSettings(
            repo = EdRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initEd),
                randomUuid = { uuidNew },
            )
        )
    }
    override val appSettingsSearch: HmlrAppSettings by lazy {
        grbAppSettings(
            repo = EdRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initEd),
                randomUuid = { uuidNew },
            )
        )
    }

    private val cleanRepo = EdRepoPGTest.repoUnderTestContainer()

    @BeforeTest
    fun beforeTest() {
        val pgRepo = cleanRepo.repo as RepoEdSql
        pgRepo.clear()
    }
}