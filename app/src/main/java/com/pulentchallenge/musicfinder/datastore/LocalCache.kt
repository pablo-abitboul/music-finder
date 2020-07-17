package com.pulentchallenge.musicfinder.datastore

import android.util.Log
import androidx.paging.DataSource
import com.pulentchallenge.musicfinder.datastore.dao.MusicTrackDao
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import java.util.concurrent.Executor

class LocalCache(
    private val musicTrackDao: MusicTrackDao,
    private val ioExecutor: Executor
) {

    fun insert(musicTracks: List<MusicTrackEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("LocalCache", "inserting ${musicTracks.size} music tracks")
            musicTrackDao.insert(musicTracks)
            insertFinished()
        }
    }

    fun tracksByName(name: String): DataSource.Factory<Int, MusicTrackEntity> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${name.replace(' ', '%')}%"
        return musicTrackDao.tracksByName(query)
    }

    fun getExecutor(): Executor {
        return ioExecutor
    }
}