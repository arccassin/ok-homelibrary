package com.otus.otuskotlin.homelibrary.backend.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "homelibrary-pass",
    val database: String = "homelibrary_eds",
    val schema: String = "public",
    val table: String = "eds",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
