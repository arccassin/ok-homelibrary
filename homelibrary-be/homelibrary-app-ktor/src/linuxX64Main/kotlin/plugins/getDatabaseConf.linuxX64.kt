package com.otus.otuskotlin.homelibrary.app.ktor.plugins

import com.otus.otuskotlin.homelibrary.app.ktor.configs.ConfigPaths
import com.otus.otuskotlin.homelibrary.common.repo.IRepoEd
import io.ktor.server.application.*

actual fun Application.getDatabaseConf(type: EdDbType): IRepoEd {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
//        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory'"
        )
    }
}

//fun Application.initPostgres(): IRepoCI {
//    val config = PostgresConfig(environment.config)
//    return RepoCISql(
//        properties = SqlProperties(
//            host = config.host,
//            port = config.port,
//            user = config.user,
//            password = config.password,
//            schema = config.schema,
//            database = config.database,
//        ),
//    )
//}
