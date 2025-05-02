package com.henriquebarucco.movielie.external.moviedb.mapper

import com.henriquebarucco.movielie.external.moviedb.dto.DetailsMovieResponse
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.enum.Language
import com.henriquebarucco.movielie.movie.enum.Status
import com.henriquebarucco.movielie.movie.enum.VideoType
import com.henriquebarucco.movielie.movie.vo.ExternalReference
import com.henriquebarucco.movielie.movie.vo.Video
import com.henriquebarucco.movielie.provider.Provider
import com.henriquebarucco.movielie.shared.exceptions.MissingPosterException
import java.time.LocalDate

private const val MOVIEDB_IMAGE_URL = "https://image.tmdb.org/t/p/original"

fun DetailsMovieResponse.toDomain() =
    Movie(
        externalReference =
            ExternalReference(
                id = this.id.toString(),
                provider = Provider.MOVIEDB,
            ),
        title = this.title,
        originalTitle = this.originalTitle,
        originalLanguage = Language.from(this.originalLanguage),
        poster = this.posterPath?.let { "$MOVIEDB_IMAGE_URL$it" } ?: throw MissingPosterException(this.id.toString()),
        backdrop = this.backdropPath?.let { "$MOVIEDB_IMAGE_URL$it" },
        overview = this.overview,
        imdbId = this.imdbId,
        status = Status.from(this.status),
        duration = this.runtime,
        releaseDate = LocalDate.parse(this.releaseDate),
        videos = this.videos.results.map { Video(id = it.id, name = it.name, url = it.key, type = VideoType.from(it.type)) },
        genres = this.genres.map { it.name },
        keywords = this.keywords.keywords.map { it.name },
    )
