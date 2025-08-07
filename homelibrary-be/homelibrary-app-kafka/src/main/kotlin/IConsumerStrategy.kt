package com.otus.otuskotlin.homelibrary.app.kafka

import com.otus.otuskotlin.homelibrary.common.HmlrContext

/**
 * Интерфейс стратегии для обслуживания версии API
 */
interface IConsumerStrategy {
    /**
     * Топики, для которых применяется стратегия
     */
    fun topics(config: AppKafkaConfig): InputOutputTopics
    /**
     * Сериализатор для версии API
     */
    fun serialize(source: HmlrContext): String
    /**
     * Десериализатор для версии API
     */
    fun deserialize(value: String, target: HmlrContext)
}
