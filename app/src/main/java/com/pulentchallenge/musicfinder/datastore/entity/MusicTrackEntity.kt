package com.pulentchallenge.musicfinder.datastore.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "music_track")
data class MusicTrackEntity(
    @PrimaryKey @field:SerializedName("trackId") val id: Long,
    @field:SerializedName("artistName") val artistName: String?,
    @field:SerializedName("trackName") val trackName: String?,
    @field:SerializedName("collectionName") val collectionName: String?,
    @field:SerializedName("previewUrl") val previewUrl: String?,
    @field:SerializedName("artworkUrl100") val artworkUrl100: String?
)