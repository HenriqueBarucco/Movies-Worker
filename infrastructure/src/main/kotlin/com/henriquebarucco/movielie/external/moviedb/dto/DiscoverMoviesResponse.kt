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
    val title: String,
    @JsonProperty(value = "original_title")
    val originalTitle: String,
    val overview: String,
    @JsonProperty(value = "release_date")
    val releaseDate: String,
    @JsonProperty(value = "poster_path")
    val posterPath: String?,
    val popularity: Double,
)
