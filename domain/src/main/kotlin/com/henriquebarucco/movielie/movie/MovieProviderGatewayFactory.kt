package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.provider.Provider

interface MovieProviderGatewayFactory {
    fun create(provider: Provider): MovieProviderGateway
}
