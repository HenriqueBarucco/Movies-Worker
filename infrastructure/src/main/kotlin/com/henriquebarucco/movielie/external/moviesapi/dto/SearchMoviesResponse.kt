package com.henriquebarucco.movielie.external.moviesapi.dto

data class SearchMoviesResponse(
    val results: List<DiscoverMoviesResultResponse>,
)

data class DiscoverMoviesResultResponse(
    val id: String,
    val title: String,
    val checksum: String,
)
