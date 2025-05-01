package com.henriquebarucco.movielie.external.moviesapi

import com.henriquebarucco.movielie.external.moviesapi.dto.SearchMoviesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "movie-api-client", url = "\${movies-api.url}", configuration = [])
interface MoviesAPIClient {
    @GetMapping(
        value = ["\${movies-api.endpoints.movie.search}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun searchMovie(
        @RequestParam params: Map<String, String>,
    ): ResponseEntity<SearchMoviesResponse>
}
