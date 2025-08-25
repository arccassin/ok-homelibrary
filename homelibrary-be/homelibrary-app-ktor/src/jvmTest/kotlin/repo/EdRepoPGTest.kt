package com.otus.otuskotlin.homelibrary.app.ktor.repo

import com.benasher44.uuid.uuid4
import com.otus.otuskotlin.homelibrary.backend.repo.postgresql.RepoEdSql
import com.otus.otuskotlin.homelibrary.backend.repo.postgresql.SqlProperties
import com.otus.otuskotlin.homelibrary.common.models.HmlrEd
import com.otus.otuskotlin.homelibrary.repo.common.EdRepoInitialized
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import repo.V1EdRepoPGTest
import java.io.File
import java.time.Duration
import kotlin.test.Ignore

@RunWith(Enclosed::class)
class EdRepoPGTest {

    class V1Test : V1EdRepoPGTest() {}

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
            @Suppress("Since15")
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
            ),
            initObjects = initObjects,
        )

        @JvmStatic
        @BeforeClass
        fun start() {
            container.start()
            println("Container started")
        }

        @JvmStatic
        @AfterClass
        fun finish() {
            println("Container stopped")
            container.stop()
        }
    }
}