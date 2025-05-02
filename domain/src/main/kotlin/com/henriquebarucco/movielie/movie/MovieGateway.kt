package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.provider.MovieReferenceProvider
import com.henriquebarucco.movielie.provider.Provider

interface MovieGateway {
    fun create(movie: Movie)

    fun update(
        id: String,
        movie: Movie,
    )

    fun save(movie: Movie)

    fun enrich(movieReferenceProvider: MovieReferenceProvider)

    fun findByExternalReference(
        externalId: String,
        provider: Provider,
    ): MovieReference?
}
