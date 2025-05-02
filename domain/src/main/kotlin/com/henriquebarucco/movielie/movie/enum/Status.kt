package com.henriquebarucco.movielie.movie.enum

enum class Status(
    val value: String,
) {
    RELEASED("Released"),
    ;

    companion object {
        fun from(value: String): Status =
            entries.firstOrNull { it.value == value } ?: throw IllegalArgumentException("Unknown status value: $value")
    }
}
