package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.provider.Provider

interface MovieProviderGateway {
    fun supports(provider: Provider): Boolean

    fun sync(): List<Movie>
}
