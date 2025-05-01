package com.henriquebarucco.movielie.configuration.usecases

import com.henriquebarucco.movielie.movie.DefaultSyncMoviesUseCase
import com.henriquebarucco.movielie.movie.MovieGateway
import com.henriquebarucco.movielie.movie.MovieProviderGatewayFactory
import com.henriquebarucco.movielie.movie.SyncMoviesUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MovieUseCasesConfig(
    private val movieProviderGatewayFactory: MovieProviderGatewayFactory,
    private val movieGateway: MovieGateway,
) {
    @Bean
    fun SyncMoviesUseCase(): SyncMoviesUseCase = DefaultSyncMoviesUseCase(movieProviderGatewayFactory, movieGateway)
}
