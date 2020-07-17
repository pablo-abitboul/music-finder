package com.pulentchallenge.musicfinder.datastore

import com.pulentchallenge.musicfinder.datastore.dao.MusicTrackDao
import com.pulentchallenge.musicfinder.network.JobExecutor
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.Executor

val databaseModule = module {
    single { MusicFinderDatabase.getDatabase(get()).musicTrackDao() } bind MusicTrackDao::class
    single { LocalCache(get(), provideThreadPoolExecutor()) } bind LocalCache::class
}

fun provideThreadPoolExecutor(): Executor {
    return JobExecutor()
}