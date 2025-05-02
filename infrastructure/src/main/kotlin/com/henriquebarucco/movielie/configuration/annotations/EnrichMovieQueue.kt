package com.henriquebarucco.movielie.configuration.annotations

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier("enrichMovie")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class EnrichMovieQueue
