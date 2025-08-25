package com.otus.otuskotlin.homelibrary.backend.repo.postgresql

import com.benasher44.uuid.uuid4
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import com.otus.otuskotlin.homelibrary.backend.repo.tests.*
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import com.otus.otuskotlin.homelibrary.repo.common.IRepoEdInitializable
import java.io.File
import java.time.Duration
import kotlin.test.Ignore


private fun IRepoEdInitializable.clear() {
    val pgRepo = (this as EdRepoInitialized).repo as RepoEdSql
    pgRepo.clear()
}

@RunWith(Enclosed::class)
class RepoEdSQLTest {

    class RepoEdSQLCreateTest : RepoEdCreateTest() {
        override val repo = repoUnderTestContainer(
            initObjects,
            randomUuid = { lockNew.asString() },
        )
    }

    class RepoEdSQLReadTest : RepoEdReadTest() {
        override val repo = repoUnderTestContainer(initObjects)
    }

    class RepoEdSQLUpdateTest : RepoEdUpdateTest() {
        override val repo = repoUnderTestContainer(
            initObjects,
            randomUuid = { lockNew.asString() },
        )
    }

    class RepoEdSQLDeleteTest : RepoEdDeleteTest() {
        override val repo = repoUnderTestContainer(initObjects)
    }

    class RepoEdSQLSearchTest : RepoEdSearchTest() {
        override val repo = repoUnderTestContainer(initObjects)
    }

    @Ignore
    companion object {
        private const val PG_SERVICE = "psql"
        private const val MG_SERVICE = "liquibase"

        // val LOGGER = org.slf4j.LoggerFactory.getLogger(ComposeContainer::class.java)
        private val container: ComposeContainer by lazy {
            val res = this::class.java.classLoader.getResource("docker-compose-pg.yml")
                ?: throw Exception("No resource found")
            val file = File(res.toURI())
            //  val logConsumer = Slf4jLogConsumer(LOGGER)
            ComposeContainer(
                file,
            )
                .withExposedService(PG_SERVICE, 5432)
                .withStartupTimeout(Duration.ofSeconds(300))
//                .withLogConsumer(MG_SERVICE, logConsumer)
//                .withLogConsumer(PG_SERVICE, logConsumer)
                .waitingFor(
                    MG_SERVICE,
                    Wait.forLogMessage(".*Liquibase command 'update' was executed successfully.*", 1)
                )
        }

        private const val HOST = "localhost"
        private const val USER = "postgres"
        private const val PASS = "homelibrary-pass"
        private val PORT by lazy {
            container.getServicePort(PG_SERVICE, 5432) ?: 5432
        }

        fun repoUnderTestContainer(
            initObjects: Collection<HmlrEd> = emptyList(),
            randomUuid: () -> String = { uuid4().toString() },
        ) = EdRepoInitialized(
            repo = RepoEdSql(
                SqlProperties(
                    host = HOST,
                    user = USER,
                    password = PASS,
                    port = PORT,
                ),
                randomUuid = randomUuid
            ).apply {clear()},
            initObjects = initObjects,
        )

        @JvmStatic
        @BeforeClass
        fun start() {
            container.start()
        }

        @JvmStatic
        @AfterClass
        fun finish() {
            container.stop()
        }
    }
}

