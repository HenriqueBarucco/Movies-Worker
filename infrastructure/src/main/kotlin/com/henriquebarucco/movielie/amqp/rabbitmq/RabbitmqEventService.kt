package com.henriquebarucco.movielie.amqp.rabbitmq

import com.henriquebarucco.movielie.service.EventService
import com.henriquebarucco.movielie.shared.utils.Json
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitmqEventService(
    private val rabbitTemplate: RabbitTemplate,
    private val exchange: String,
    private val routingKey: String,
) : EventService {
    override fun <T> publish(message: T) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, Json.writeValueAsString(message)!!)
    }
}
