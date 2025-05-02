package com.henriquebarucco.movielie.movie.vo

import com.henriquebarucco.movielie.movie.enum.VideoType

data class Video(
    val id: String,
    val name: String,
    val url: String,
    val type: VideoType,
)
