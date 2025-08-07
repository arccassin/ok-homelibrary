package com.otus.otuskotlin.homelibrary.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyBasic()))
    consumer.start()
}
