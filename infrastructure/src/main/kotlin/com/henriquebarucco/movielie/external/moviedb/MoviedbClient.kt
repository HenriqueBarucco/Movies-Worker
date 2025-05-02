package com.henriquebarucco.movielie.external.moviedb

import com.henriquebarucco.movielie.external.moviedb.dto.DetailsMovieResponse
import com.henriquebarucco.movielie.external.moviedb.dto.DiscoverMoviesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "moviedb-client", url = "\${moviedb-api.url}", configuration = [])
interface MoviedbClient {
    @GetMapping(
        value = ["\${moviedb-api.endpoints.movie.discover}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun discoverMovies(
        @RequestParam("language") language: String = "pt-BR",
        @RequestParam("sort_by") sortBy: String = "popularity.desc",
        @RequestParam("page") page: Int,
        @RequestParam("primary_release_date.gte") primaryReleaseDateGte: String,
        @RequestParam("primary_release_date.lte") primaryReleaseDateLte: String,
    ): ResponseEntity<DiscoverMoviesResponse>

    @GetMapping(
        value = ["\${moviedb-api.endpoints.movie.details}/{movieId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun detailsMovie(
        @RequestParam("language") language: String = "pt-BR",
        @RequestParam("append_to_response") appendToResponse: String = "videos,keywords",
        @PathVariable("movieId") movieId: String,
    ): ResponseEntity<DetailsMovieResponse>
}
