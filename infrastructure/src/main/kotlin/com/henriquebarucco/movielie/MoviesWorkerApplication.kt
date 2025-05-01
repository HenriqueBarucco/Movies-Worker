package com.henriquebarucco.movielie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
class MoviesWorkerApplication

fun main(args: Array<String>) {
    runApplication<MoviesWorkerApplication>(*args)
}
