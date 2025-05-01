package com.henriquebarucco.movielie.database.redis.entity

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.time.LocalDate

@RedisHash("SyncProgress")
data class SyncProgress(
    var id: String = "progress",
    var startDate: LocalDate,
    var endDate: LocalDate,
    var page: Int,
) : Serializable
