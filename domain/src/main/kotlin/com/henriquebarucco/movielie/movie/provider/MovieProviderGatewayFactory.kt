package com.henriquebarucco.movielie.movie.provider

import com.henriquebarucco.movielie.provider.Provider

interface MovieProviderGatewayFactory {
    fun create(provider: Provider): MovieProviderGateway
}
