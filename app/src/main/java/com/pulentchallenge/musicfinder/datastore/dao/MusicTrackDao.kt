package com.pulentchallenge.musicfinder.datastore.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity

@Dao
interface MusicTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(musicTracks: List<MusicTrackEntity>)

    @Query("SELECT * FROM music_track WHERE trackName LIKE :queryString ORDER BY trackName ASC")
    fun tracksByName(queryString: String): DataSource.Factory<Int, MusicTrackEntity>
}