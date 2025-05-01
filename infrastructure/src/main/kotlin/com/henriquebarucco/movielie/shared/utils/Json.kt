package com.henriquebarucco.movielie.shared.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.blackbird.BlackbirdModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.util.concurrent.Callable

enum class Json {
    INSTANCE,
    ;

    private val mapper =
        Jackson2ObjectMapperBuilder()
            .dateFormat(StdDateFormat())
            .featuresToDisable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            ).modules(JavaTimeModule(), Jdk8Module(), BlackbirdModule())
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build<ObjectMapper?>()

    companion object {
        fun mapper(): ObjectMapper? = INSTANCE.mapper.copy()

        fun writeValueAsBytes(obj: Any?): ByteArray? = invoke<ByteArray?>({ INSTANCE.mapper.writeValueAsBytes(obj) })

        fun writeValueAsString(obj: Any?): String? = invoke<String?>({ INSTANCE.mapper.writeValueAsString(obj) })

        fun <T> readValue(
            json: ByteArray?,
            clazz: Class<T?>?,
        ): T? = invoke<T?>({ INSTANCE.mapper.readValue<T?>(json, clazz) })

        fun <T> readValue(
            json: String?,
            clazz: Class<T?>?,
        ): T? = invoke<T?>({ INSTANCE.mapper.readValue<T?>(json, clazz) })

        fun <T> readValue(
            json: String?,
            clazz: TypeReference<T?>?,
        ): T? = invoke<T?>({ INSTANCE.mapper.readValue(json, clazz) })

        fun <T> readTree(
            json: String?,
            clazz: Class<T?>?,
        ): T? {
            return invoke<T?>(
                Callable invoke@{
                    val `val` = INSTANCE.mapper.readTree(json)
                    if (`val` is TextNode) {
                        return@invoke readTree<T?>(`val`.asText(), clazz)
                    } else {
                        return@invoke INSTANCE.mapper.convertValue<T?>(`val`, clazz)
                    }
                },
            )
        }

        private fun <T> invoke(callable: Callable<T?>): T? {
            try {
                return callable.call()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}
