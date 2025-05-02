package com.henriquebarucco.movielie.entrypoint.amqp.dto

import com.henriquebarucco.movielie.movie.enrich.dto.EnrichMovieCommand
import kotlinx.serialization.Serializable

@Serializable
data class EnrichMovieDto(
    val id: String,
    val provider: String,
) {
    fun toCommand() =
        EnrichMovieCommand(
            id = this.id,
            provider = this.provider,
        )
}
