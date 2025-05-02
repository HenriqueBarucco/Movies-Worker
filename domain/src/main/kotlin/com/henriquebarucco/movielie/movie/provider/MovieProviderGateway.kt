package com.henriquebarucco.movielie.movie.provider

import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.provider.Provider

interface MovieProviderGateway {
    fun supports(provider: Provider): Boolean

    fun sync(): List<MovieReferenceProvider>

    fun enrich(movieId: String): Movie
}
