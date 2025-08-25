package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.homelibrary.app.ktor.configs.ConfigPaths
import com.otus.otuskotlin.homelibrary.app.ktor.configs.PostgresConfig
import com.otus.otuskotlin.homelibrary.backend.repo.postgresql.RepoEdSql
import com.otus.otuskotlin.homelibrary.backend.repo.postgresql.SqlProperties
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import kotlin.text.lowercase

actual fun Application.getDatabaseConf(type: EdDbType): IRepoEd {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw kotlin.IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

fun Application.initPostgres(): IRepoEd {
    val config = PostgresConfig(environment.config)
    return RepoEdSql(
        properties = SqlProperties(
            host = config.host,
            port = config.port,
            user = config.user,
            password = config.password,
            schema = config.schema,
            database = config.database,
        ),
    )
}

