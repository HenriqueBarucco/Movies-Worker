package com.henriquebarucco.movielie.external.moviedb.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DiscoverMoviesResponse(
    val page: Int,
    @JsonProperty(value = "total_pages")
    val totalPages: Int,
    @JsonProperty(value = "total_results")
    val totalResults: Int,
    val results: List<DiscoverMoviesResultResponse>,
)

data class DiscoverMoviesResultResponse(
    val id: Int,
)
