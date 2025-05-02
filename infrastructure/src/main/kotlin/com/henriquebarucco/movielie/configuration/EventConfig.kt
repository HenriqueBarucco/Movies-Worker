package com.henriquebarucco.movielie.configuration

import com.henriquebarucco.movielie.amqp.rabbitmq.RabbitmqEventService
import com.henriquebarucco.movielie.configuration.annotations.CreateMovieQueue
import com.henriquebarucco.movielie.configuration.annotations.EnrichMovieQueue
import com.henriquebarucco.movielie.configuration.annotations.SaveMovieQueue
import com.henriquebarucco.movielie.configuration.annotations.UpdateMovieQueue
import com.henriquebarucco.movielie.configuration.properties.amqp.QueueProperties
import com.henriquebarucco.movielie.service.EventService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventConfig {
    @Bean
    @CreateMovieQueue
    fun createMovieEventService(
        @CreateMovieQueue queueProperties: QueueProperties,
        rabbitTemplate: RabbitTemplate,
    ): EventService =
        RabbitmqEventService(
            rabbitTemplate = rabbitTemplate,
            routingKey = queueProperties.routingKey!!,
            exchange = queueProperties.exchange!!,
        )

    @Bean
    @UpdateMovieQueue
    fun updateMovieEventService(
        @UpdateMovieQueue queueProperties: QueueProperties,
        rabbitTemplate: RabbitTemplate,
    ): EventService =
        RabbitmqEventService(
            rabbitTemplate = rabbitTemplate,
            routingKey = queueProperties.routingKey!!,
            exchange = queueProperties.exchange!!,
        )

    @Bean
    @EnrichMovieQueue
    fun enrichMovieEventService(
        @EnrichMovieQueue queueProperties: QueueProperties,
        rabbitTemplate: RabbitTemplate,
    ): EventService =
        RabbitmqEventService(
            rabbitTemplate = rabbitTemplate,
            routingKey = queueProperties.routingKey!!,
            exchange = queueProperties.exchange!!,
        )

    @Bean
    @SaveMovieQueue
    fun saveMovieEventService(
        @SaveMovieQueue queueProperties: QueueProperties,
        rabbitTemplate: RabbitTemplate,
    ): EventService =
        RabbitmqEventService(
            rabbitTemplate = rabbitTemplate,
            routingKey = queueProperties.routingKey!!,
            exchange = queueProperties.exchange!!,
        )
}
