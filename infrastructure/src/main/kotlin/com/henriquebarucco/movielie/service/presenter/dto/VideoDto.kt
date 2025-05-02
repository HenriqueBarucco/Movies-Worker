package com.henriquebarucco.movielie.service.presenter.dto

import com.henriquebarucco.movielie.movie.vo.Video

data class VideoDto(
    val id: String,
    val name: String,
    val url: String,
    val type: String,
)

fun Video.toVideoDto(): VideoDto =
    VideoDto(
        id = this.id,
        name = this.name,
        url = this.url,
        type = this.type.name,
    )
