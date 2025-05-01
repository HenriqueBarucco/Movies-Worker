package com.henriquebarucco.movielie.service

interface EventService {
    fun <T> publish(message: T)
}
