package com.henriquebarucco.movielie.external.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class FeignRequestInterceptor(
    @Value("\${moviedb-api.token}") private val moviedbToken: String,
) : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        val client = client(requestTemplate)

        when (client) {
            "moviedb-client" ->
                requestTemplate.header("Authorization", "Bearer $moviedbToken")
        }
    }

    private fun client(requestTemplate: RequestTemplate): String = requestTemplate.feignTarget().name()
}
