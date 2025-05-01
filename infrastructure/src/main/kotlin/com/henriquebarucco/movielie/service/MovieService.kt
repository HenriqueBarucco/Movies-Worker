package com.henriquebarucco.movielie.service

import com.henriquebarucco.movielie.configuration.annotations.CreateMovieQueue
import com.henriquebarucco.movielie.configuration.annotations.UpdateMovieQueue
import com.henriquebarucco.movielie.external.moviesapi.MoviesAPIClient
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieReference
import com.henriquebarucco.movielie.provider.Provider
import com.henriquebarucco.movielie.service.presenter.toCreateMessage
import com.henriquebarucco.movielie.service.presenter.toUpdateMessage
import com.henriquebarucco.movielie.shared.utils.Json
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import org.slf4j.MDC
import org.springframework.stereotype.Service

@Service
class MovieService(
    @CreateMovieQueue private val createEventService: EventService,
    @UpdateMovieQueue private val updateEventService: EventService,
    private val moviesAPIClient: MoviesAPIClient,
) : MovieGateway {
    companion object {
        const val CREATE_MOVIE_MESSAGE = "CREATE_MOVIE_MESSAGE"
        const val UPDATE_MOVIE_MESSAGE = "UPDATE_MOVIE_MESSAGE"
    }

    private val logger = getLogger()

    override fun create(movie: Movie) {
        try {
            val message = movie.toCreateMessage()
            MDC.put(CREATE_MOVIE_MESSAGE, Json.writeValueAsString(message))

            this.logger.info("[CREATE_MOVIE] Sending movie to be created: ${movie.title} (${movie.externalId}|${movie.provider})")
            this.createEventService.publish(message)
        } finally {
            MDC.remove(CREATE_MOVIE_MESSAGE)
        }
    }

    override fun update(
        id: String,
        movie: Movie,
    ) {
        try {
            val message = movie.toUpdateMessage(id)
            MDC.put(UPDATE_MOVIE_MESSAGE, Json.writeValueAsString(message))

            this.logger.info("[UPDATE_MOVIE] Sending movie to be updated: ${movie.title} ($id)")
            this.updateEventService.publish(message)
        } finally {
            MDC.remove(UPDATE_MOVIE_MESSAGE)
        }
    }

    override fun findByExternalReference(
        externalId: String,
        provider: Provider,
    ): MovieReference? {
        val query = mapOf("externalReferences:elemMatch" to "id:$externalId,provider:${provider.name}")
        val response = this.moviesAPIClient.searchMovie(query)

        if (response.statusCode.isError) {
            throw RuntimeException("Error fetching movies from Movies-API: ${response.statusCode}")
        }

        val results = response.body ?: return null

        if (results.results.isNotEmpty()) {
            val firstResult = results.results.first()

            return MovieReference(
                id = firstResult.id,
                title = firstResult.title,
                checksum = firstResult.checksum,
            )
        }

        return null
    }
}
