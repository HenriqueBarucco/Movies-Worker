package com.henriquebarucco.movielie.movie

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.LocalDate

data class Movie(
    val externalId: String,
    val provider: String,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val poster: String,
    val releaseDate: LocalDate,
    val status: String,
) {
    fun checksum(): String {
        val input = "$title|$originalTitle|$overview|$releaseDate|$status"
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
