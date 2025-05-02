package com.henriquebarucco.movielie.movie.enum

enum class VideoType(
    val value: String,
) {
    TRAILER("Trailer"),
    ;

    companion object {
        fun from(value: String): VideoType =
            VideoType.entries.firstOrNull { it.value == value } ?: throw IllegalArgumentException("Unknown type value: $value")
    }
}
