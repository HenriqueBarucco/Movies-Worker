package com.henriquebarucco.movielie.movie

import com.henriquebarucco.movielie.movie.enum.Language
import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Video
import java.time.LocalDate

data class Movie(
    val externalReference: ExternalReference,
    val title: String,
    val originalTitle: String,
    val originalLanguage: Language,
    val poster: String,
    val backdrop: String?,
    val overview: String,
    val imdbId: String?,
    val status: Status,
    val duration: Int,
    val releaseDate: LocalDate,
    val videos: List<Video>,
    val genres: List<String>,
    val keywords: List<String>,
)
