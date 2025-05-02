package com.henriquebarucco.movielie.movie.vo

import com.henriquebarucco.movielie.provider.Provider

data class ExternalReference(
    val id: String,
    val provider: Provider,
)
