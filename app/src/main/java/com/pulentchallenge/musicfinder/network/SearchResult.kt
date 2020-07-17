package com.pulentchallenge.musicfinder.network

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity

data class SearchResult(
    val data: LiveData<PagedList<MusicTrackEntity>>,
    val networkError: LiveData<String>
)