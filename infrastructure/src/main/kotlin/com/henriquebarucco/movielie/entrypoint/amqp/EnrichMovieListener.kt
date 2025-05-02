package com.henriquebarucco.movielie.entrypoint.amqp

import com.henriquebarucco.movielie.entrypoint.amqp.dto.EnrichMovieDto
import com.henriquebarucco.movielie.movie.enrich.EnrichMovieUseCase
import com.henriquebarucco.movielie.shared.exceptions.MissingPosterException
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.MDC
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class EnrichMovieListener(
    private val json: Json,
    private val enrichMovieUseCase: EnrichMovieUseCase,
) {
    companion object {
        private const val MESSAGE_BODY = "MESSAGE_BODY"
    }

    private val logger = getLogger()

    @RabbitListener(
        queues = ["\${rabbitmq.queues.movies.enrich-movie.queue}"],
        errorHandler = "DeadLetterErrorHandler",
        concurrency = "\${rabbitmq.queues.movies.enrich-movie.concurrency}",
    )
    fun enrichMovieMessage(message: Message) {
        val messageBody = json.decodeFromString<EnrichMovieDto>(String(message.body))
        MDC.put(MESSAGE_BODY, json.encodeToString(messageBody))

        try {
            this.logger.info("[ENRICH_MOVIE] Received new message to enrich movie (${messageBody.id}|${messageBody.provider})")
            this.enrichMovieUseCase.execute(messageBody.toCommand())
            this.logger.info("[ENRICH_MOVIE] Movie enriched successfully (${messageBody.id}|${messageBody.provider})")
        } catch (ex: MissingPosterException) {
            this.logger.warn("[ENRICH_MOVIE] Movie poster not found (${messageBody.id}|${messageBody.provider}) ignoring it")
        } catch (ex: Exception) {
            this.logger.error("[ENRICH_MOVIE] Failed to enrich movie", ex)
            throw ex
        } finally {
            MDC.clear()
        }
    }
}
