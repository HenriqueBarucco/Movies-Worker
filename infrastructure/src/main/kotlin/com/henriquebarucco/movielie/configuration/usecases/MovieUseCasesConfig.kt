package com.henriquebarucco.movielie.configuration.usecases

import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.enrich.DefaultEnrichMovieUseCase
import com.henriquebarucco.movielie.movie.enrich.EnrichMovieUseCase
import com.henriquebarucco.movielie.movie.provider.MovieProviderGatewayFactory
import com.henriquebarucco.movielie.movie.sync.DefaultSyncMoviesUseCase
import com.henriquebarucco.movielie.movie.sync.SyncMoviesUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MovieUseCasesConfig(
    private val movieProviderGatewayFactory: MovieProviderGatewayFactory,
    private val movieGateway: MovieGateway,
) {
    @Bean
    fun SyncMoviesUseCase(): SyncMoviesUseCase = DefaultSyncMoviesUseCase(movieProviderGatewayFactory, movieGateway)

    @Bean
    fun enrichMovieUseCase(): EnrichMovieUseCase = DefaultEnrichMovieUseCase(movieProviderGatewayFactory, movieGateway)
}
