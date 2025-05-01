package com.henriquebarucco.movielie.configuration

import com.henriquebarucco.movielie.configuration.annotations.CreateMovieQueue
import com.henriquebarucco.movielie.configuration.annotations.UpdateMovieQueue
import com.henriquebarucco.movielie.configuration.properties.amqp.QueueProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig {
    @Bean
    @CreateMovieQueue
    @ConfigurationProperties(prefix = "rabbitmq.queues.movies.create-movie")
    fun createMovieQueueProperties() = QueueProperties()

    @Bean
    @UpdateMovieQueue
    @ConfigurationProperties(prefix = "rabbitmq.queues.movies.update-movie")
    fun updateMovieQueueProperties() = QueueProperties()
}
