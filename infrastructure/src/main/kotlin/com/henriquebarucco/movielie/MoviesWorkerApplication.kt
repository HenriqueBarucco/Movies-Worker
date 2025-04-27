package com.henriquebarucco.movielie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoviesWorkerApplication

fun main(args: Array<String>) {
    runApplication<MoviesWorkerApplication>(*args)
}
