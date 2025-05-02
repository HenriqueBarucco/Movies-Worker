package com.henriquebarucco.movielie.service.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.service.presenter.dto.ExternalReferenceDto
import com.henriquebarucco.movielie.service.presenter.dto.VideoDto
import com.henriquebarucco.movielie.service.presenter.dto.toExternalReferenceDto
import com.henriquebarucco.movielie.service.presenter.dto.toVideoDto

data class CreateMovieMessage(
    @JsonProperty("external_reference")
    val externalReference: ExternalReferenceDto,
    val title: String,
    @JsonProperty("original_title")
    val originalTitle: String,
    @JsonProperty("original_language")
    val originalLanguage: String,
    val poster: String,
    val backdrop: String?,
    val overview: String,
    @JsonProperty("imdb_id")
    val imdbId: String?,
    val status: String,
    val duration: Int,
    @JsonProperty("release_date")
    val releaseDate: String,
    val videos: List<VideoDto>,
    val genres: List<String>,
    val keywords: List<String>,
)

fun Movie.toCreateMessage() =
    CreateMovieMessage(
        externalReference = this.externalReference.toExternalReferenceDto(),
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = this.originalLanguage.name,
        poster = this.poster,
        backdrop = this.backdrop,
        overview = this.overview,
        imdbId = this.imdbId,
        status = this.status.name,
        duration = this.duration,
        releaseDate = this.releaseDate.toString(),
        videos = this.videos.map { it.toVideoDto() },
        genres = this.genres,
        keywords = this.keywords,
    )
