package com.henriquebarucco.movielie.configuration.factories

import com.henriquebarucco.movielie.movie.MovieProviderGateway
import com.henriquebarucco.movielie.movie.MovieProviderGatewayFactory
import com.henriquebarucco.movielie.provider.Provider
import org.springframework.stereotype.Component

@Component
class MovieProviderGatewayFactoryImpl(
    private val movieProviderGateways: List<MovieProviderGateway>,
) : MovieProviderGatewayFactory {
    override fun create(provider: Provider): MovieProviderGateway =
        this.movieProviderGateways.firstOrNull { it.supports(provider) }
            ?: throw IllegalArgumentException("No provider found for $provider")
}
