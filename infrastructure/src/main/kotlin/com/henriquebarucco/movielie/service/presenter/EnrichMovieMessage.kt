package com.henriquebarucco.movielie.service.presenter

import com.henriquebarucco.movielie.movie.provider.MovieReferenceProvider

data class EnrichMovieMessage(
    val id: String,
    val provider: String,
)

fun MovieReferenceProvider.toEnrichMessage() =
    EnrichMovieMessage(
        id = id,
        provider = provider.name,
    )
