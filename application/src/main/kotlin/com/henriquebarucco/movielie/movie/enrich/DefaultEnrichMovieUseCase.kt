package com.henriquebarucco.movielie.movie.enrich

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.enrich.dto.EnrichMovieCommand
import com.henriquebarucco.movielie.movie.provider.MovieProviderGatewayFactory
import com.henriquebarucco.movielie.provider.Provider

class DefaultEnrichMovieUseCase(
    private val movieProviderGatewayFactory: MovieProviderGatewayFactory,
    private val movieGateway: MovieGateway,
) : EnrichMovieUseCase() {
    override fun execute(input: EnrichMovieCommand) {
        val (id) = input
        val provider = Provider.valueOf(input.provider)

        val movieProviderGateway = this.movieProviderGatewayFactory.create(provider)

        val movie = movieProviderGateway.enrich(id)
        this.movieGateway.save(movie)
    }
}
