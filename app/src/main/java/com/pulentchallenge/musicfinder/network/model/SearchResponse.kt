package com.pulentchallenge.musicfinder.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity

data class SearchResponse(
    @SerializedName("resultCount") @Expose val resultCount: Int = 0,
    @SerializedName("results") @Expose val musicTrackModels: List<MusicTrackEntity>? = emptyList(),
    val nextPage: Int? = null
)