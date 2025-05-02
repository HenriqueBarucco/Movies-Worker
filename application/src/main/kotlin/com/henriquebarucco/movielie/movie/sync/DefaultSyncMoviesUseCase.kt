package com.henriquebarucco.movielie.movie.sync

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.provider.MovieProviderGatewayFactory
import com.henriquebarucco.movielie.movie.sync.SyncMoviesUseCase
import com.henriquebarucco.movielie.movie.sync.dto.SyncMoviesCommand
import com.henriquebarucco.movielie.provider.Provider

class DefaultSyncMoviesUseCase(
    private val movieProviderGatewayFactory: MovieProviderGatewayFactory,
    private val movieGateway: MovieGateway,
) : SyncMoviesUseCase() {
    override fun execute(input: SyncMoviesCommand) {
        val provider = Provider.valueOf(input.provider)

        val movieProviderGateway = this.movieProviderGatewayFactory.create(provider)

        val moviesReferences = movieProviderGateway.sync()

        moviesReferences.forEach { movieReference ->
            this.movieGateway.enrich(movieReference)
        }
    }
}
