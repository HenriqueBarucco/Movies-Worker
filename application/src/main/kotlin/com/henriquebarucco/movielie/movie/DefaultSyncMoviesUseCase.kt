package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.dto.SyncMoviesCommand
import com.henriquebarucco.movielie.provider.Provider

class DefaultSyncMoviesUseCase(
    private val movieProviderGatewayFactory: MovieProviderGatewayFactory,
    private val movieGateway: MovieGateway,
) : SyncMoviesUseCase() {
    override fun execute(input: SyncMoviesCommand) {
        val provider = Provider.valueOf(input.provider)

        val movieProviderGateway = this.movieProviderGatewayFactory.create(provider)

        val movies = movieProviderGateway.sync()

        movies.forEach { movie ->
            val movieReference = this.movieGateway.findByExternalReference(movie.externalId, provider)

            if (movieReference == null) {
                this.movieGateway.create(movie)
            } else {
                if (shouldUpdateMovie(movie, movieReference)) {
                    this.movieGateway.update(movieReference.id, movie)
                }
            }
        }
    }

    private fun shouldUpdateMovie(
        movie: Movie,
        movieReference: MovieReference,
    ): Boolean {
        val checksum = movie.checksum()
        return movieReference.checksum != checksum
    }
}
