package com.otus.otuskotlin.homelibrary.app.kafka

import com.otus.otuskotlin.homelibrary.api.v1.apiV1RequestSerialize
import com.otus.otuskotlin.homelibrary.api.v1.apiV1ResponseDeserialize
import com.otus.otuskotlin.homelibrary.api.v1.models.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyBasic()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        EdCreateRequest(
                            ed = EdCreateObject(
                                title = "Бесы",
                                author = "Федор Достоевский",
                                isbn = "978-3-16-148410-0",
                                year = "1984"
                            ),
                            debug = EdDebug(
                                mode = EdRequestDebugMode.STUB,
                                stub = EdRequestDebugStubs.SUCCESS,
                            ),
                        ),
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<EdCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("Бесы", result.ed?.title)
        assertEquals("Федор Достоевский", result.ed?.author)
        assertEquals("978-3-16-148410-0", result.ed?.isbn)
        assertEquals("1984", result.ed?.year)
    }

    companion object {
        const val PARTITION = 0
    }
}