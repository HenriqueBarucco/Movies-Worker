package com.henriquebarucco.movielie.database.redis.repository

import com.henriquebarucco.movielie.database.redis.entity.SyncProgress
import org.springframework.data.repository.CrudRepository

interface SyncProgressRedisRepository : CrudRepository<SyncProgress, String>
