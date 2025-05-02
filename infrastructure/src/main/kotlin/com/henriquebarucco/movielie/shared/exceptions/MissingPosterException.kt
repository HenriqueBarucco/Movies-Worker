package com.henriquebarucco.movielie.shared.exceptions

class MissingPosterException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
