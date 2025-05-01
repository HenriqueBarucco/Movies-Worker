package com.henriquebarucco.movielie.entrypoint.schedule

import com.henriquebarucco.movielie.movie.SyncMoviesUseCase
import com.henriquebarucco.movielie.movie.dto.SyncMoviesCommand
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SyncMoviesScheduler(
    private val syncMoviesUseCase: SyncMoviesUseCase,
    @Value("\${scheduler.sync-movie.enabled}") private val enabled: Boolean,
    @Value("\${scheduler.sync-movie.provider}") private val provider: String,
) {
    private val logger = getLogger()

    @Scheduled(cron = "\${scheduler.sync-movie.cron}")
    fun syncMovies() {
        if (!enabled) return

        this.logger.info("[SYNC_MOVIES] Syncing movies with provider $provider...")

        try {
            this.syncMoviesUseCase.execute(SyncMoviesCommand(provider))
            this.logger.info("[SYNC_MOVIES] Movies synced successfully with provider: $provider")
        } catch (ex: Exception) {
            this.logger.error("[SYNC_MOVIES] Failed to sync movies with provider $provider", ex)
        }
    }
}
