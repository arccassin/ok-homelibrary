package com.otus.otuskotlin.homelibrary.app.kafka

import com.otus.otuskotlin.homelibrary.api.v1.apiV1RequestDeserialize
import com.otus.otuskotlin.homelibrary.api.v1.apiV1ResponseSerialize
import com.otus.otuskotlin.homelibrary.api.v1.mappers.fromTransport
import com.otus.otuskotlin.homelibrary.api.v1.mappers.toTransportEd
import com.otus.otuskotlin.homelibrary.api.v1.models.IRequest
import com.otus.otuskotlin.homelibrary.common.HmlrContext

class ConsumerStrategyBasic : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: HmlrContext): String {
        return apiV1ResponseSerialize(source.toTransportEd())
    }

    override fun deserialize(value: String, target: HmlrContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
