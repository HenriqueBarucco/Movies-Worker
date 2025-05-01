package com.henriquebarucco.movielie.database.redis

import com.henriquebarucco.movielie.database.redis.entity.SyncProgress
import com.henriquebarucco.movielie.database.redis.repository.SyncProgressRedisRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SyncProgressRepository(
    private val redisRepository: SyncProgressRedisRepository,
) {
    private val defaultStartDate = LocalDate.of(1900, 1, 1)
    private val defaultEndDate = LocalDate.of(2099, 1, 1)

    fun getCurrent(): SyncProgress =
        this.redisRepository.findById("progress").orElseGet {
            val endDate = defaultStartDate.plusMonths(1).minusDays(1)
            val newProgress =
                SyncProgress(
                    id = "progress",
                    startDate = defaultStartDate,
                    endDate = endDate,
                    page = 1,
                )
            this.redisRepository.save(newProgress)
            newProgress
        }

    fun advancePage() {
        val progress = getCurrent()
        progress.page += 1
        this.redisRepository.save(progress)
    }

    fun moveToNextMonth() {
        val progress = getCurrent()

        if (progress.startDate >= defaultEndDate) {
            val newStart = defaultStartDate
            val newEnd = defaultStartDate.plusMonths(1).minusDays(1)
            val reset =
                progress.copy(
                    startDate = newStart,
                    endDate = newEnd,
                    page = 1,
                )
            this.redisRepository.save(reset)
            return
        }

        val nextStart = progress.startDate.plusMonths(1)
        val nextEnd = nextStart.plusMonths(1).minusDays(1)
        val updated =
            progress.copy(
                startDate = nextStart,
                endDate = nextEnd,
                page = 1,
            )
        this.redisRepository.save(updated)
    }
}
