package com.henriquebarucco.movielie.service.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.service.presenter.dto.ExternalReferenceDto

data class UpdateMovieMessage(
    val id: String,
    val title: String,
    @JsonProperty("original_title")
    val originalTitle: String,
    val poster: String,
    val overview: String,
    @JsonProperty("release_date")
    val releaseDate: String,
    val status: String,
    @JsonProperty("external_reference")
    val externalReference: ExternalReferenceDto,
)

fun Movie.toUpdateMessage(id: String) =
    UpdateMovieMessage(
        id = id,
        title = title,
        originalTitle = originalTitle,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate.toString(),
        status = status,
        externalReference =
            ExternalReferenceDto(
                provider = provider,
                id = externalId,
            ),
    )
