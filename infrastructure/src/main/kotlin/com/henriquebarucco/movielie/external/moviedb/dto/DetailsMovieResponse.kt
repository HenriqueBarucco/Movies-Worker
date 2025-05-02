package com.henriquebarucco.movielie.external.moviedb.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DetailsMovieResponse(
    val adult: Boolean,
    @JsonProperty(value = "backdrop_path")
    val backdropPath: String?,
    val budget: Long,
    val genres: List<GenreDto>,
    val homepage: String,
    val id: Long,
    @JsonProperty(value = "imdb_id")
    val imdbId: String?,
    @JsonProperty(value = "origin_country")
    val originCountry: List<String>,
    @JsonProperty(value = "original_language")
    val originalLanguage: String,
    @JsonProperty(value = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @JsonProperty(value = "poster_path")
    val posterPath: String?,
    @JsonProperty(value = "release_date")
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val videos: VideosDto,
    val keywords: KeywordsDto,
)

data class GenreDto(
    val id: Long,
    val name: String,
)

data class VideosDto(
    val results: List<VideoDto>,
)

data class VideoDto(
    @JsonProperty(value = "iso_639_1")
    val iso6391: String,
    @JsonProperty(value = "iso_3166_1")
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val id: String,
)

data class KeywordsDto(
    val keywords: List<KeywordDto>,
)

data class KeywordDto(
    val id: Long,
    val name: String,
)
