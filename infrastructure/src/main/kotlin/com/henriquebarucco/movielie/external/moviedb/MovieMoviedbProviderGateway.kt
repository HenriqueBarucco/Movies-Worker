package com.henriquebarucco.movielie.external.moviedb

import com.henriquebarucco.movielie.database.redis.SyncProgressRepository
import com.henriquebarucco.movielie.external.moviedb.mapper.toDomain
import com.henriquebarucco.movielie.movie.Movie
import com.henriquebarucco.movielie.movie.provider.MovieProviderGateway
import com.henriquebarucco.movielie.movie.provider.MovieReferenceProvider
import com.henriquebarucco.movielie.provider.Provider
import com.henriquebarucco.movielie.provider.Provider.MOVIEDB
import com.henriquebarucco.movielie.shared.utils.Logger.Companion.getLogger
import org.slf4j.MDC
import org.springframework.stereotype.Service

@Service
class MovieMoviedbProviderGateway(
    private val moviedbClient: MoviedbClient,
    private val syncProgressRepository: SyncProgressRepository,
) : MovieProviderGateway {
    companion object {
        private const val SYNC_PROGRESS = "SYNC_PROGRESS"
    }

    private val logger = getLogger()

    override fun supports(provider: Provider): Boolean = provider == MOVIEDB

    override fun sync(): List<MovieReferenceProvider> {
        val current = this.syncProgressRepository.getCurrent(MOVIEDB)

        MDC.put(SYNC_PROGRESS, "page=${current.page}, startDate=${current.startDate}, endDate=${current.endDate}")
        this.logger.info("[SYNC_MOVIEDB] Starting movie sync for page ${current.page} from ${current.startDate} to ${current.endDate}")

        val response =
            this.moviedbClient.discoverMovies(
                page = current.page,
                primaryReleaseDateGte = current.startDate.toString(),
                primaryReleaseDateLte = current.endDate.toString(),
            )

        if (response.statusCode.isError) {
            this.logger.error("[SYNC_MOVIEDB] Error response from MovieDB: ${response.statusCode}")
            MDC.remove(SYNC_PROGRESS)
            throw RuntimeException("Error fetching movies: ${response.statusCode}")
        }

        val body =
            response.body ?: run {
                this.logger.warn("[SYNC_MOVIEDB] Empty response body for page ${current.page}")
                MDC.remove(SYNC_PROGRESS)
                return emptyList()
            }

        this.logger.info("[SYNC_MOVIEDB] Fetched ${body.results.size} movies (page ${current.page}/${body.totalPages})")

        if (body.totalPages >= 500) {
            this.logger.error(
                "[SYNC_MOVIEDB] Too many results (${body.totalPages} pages). " +
                    "Date range too large: ${current.startDate} to ${current.endDate}",
            )
            MDC.remove(SYNC_PROGRESS)
            throw IllegalStateException(
                "Date range ${current.startDate} to ${current.endDate} exceeds the 500 page limit (totalPages=${body.totalPages}). " +
                    "You must use smaller date intervals.",
            )
        }

        if (current.page < body.totalPages) {
            this.logger.info("[SYNC_MOVIEDB] Advancing to next page (${current.page + 1})")
            this.syncProgressRepository.advancePage(MOVIEDB)
        } else {
            this.logger.info("[SYNC_MOVIEDB] Finished current month, moving to next")
            this.syncProgressRepository.moveToNextMonth(MOVIEDB)
        }

        val movies =
            body.results
                .map { movie ->
                    MovieReferenceProvider(
                        id = movie.id.toString(),
                        provider = MOVIEDB,
                    )
                }

        this.logger.info("[SYNC_MOVIEDB] Returning ${movies.size} movies")
        MDC.remove(SYNC_PROGRESS)
        return movies
    }

    override fun enrich(movieId: String): Movie {
        this.logger.info("[ENRICHMENT_MOVIEDB] Starting movie enrichment for movie Id $movieId")

        val response =
            this.moviedbClient.detailsMovie(
                movieId = movieId,
            )

        if (response.statusCode.isError) {
            this.logger.error("[ENRICHMENT_MOVIEDB] Error response from MovieDB: ${response.statusCode}")
            throw RuntimeException("Error fetching movies: ${response.statusCode}")
        }

        val body =
            response.body ?: run {
                this.logger.warn("[ENRICHMENT_MOVIEDB] Empty response body")
                throw IllegalStateException("Empty response body")
            }

        this.logger.info("[ENRICHMENT_MOVIEDB] Movie ID $movieId enriched successfully")

        val movie = body.toDomain()
        return movie
    }
}
