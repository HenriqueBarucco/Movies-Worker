package com.henriquebarucco.movielie.service.presenter.dto

import com.henriquebarucco.movielie.movie.vo.ExternalReference

data class ExternalReferenceDto(
    val id: String,
    val provider: String,
)

fun ExternalReference.toExternalReferenceDto(): ExternalReferenceDto =
    ExternalReferenceDto(
        id = this.id,
        provider = this.provider.name,
    )
