package com.pulentchallenge.musicfinder.repository

import android.util.Log
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pulentchallenge.musicfinder.datastore.BoundaryCallback
import com.pulentchallenge.musicfinder.datastore.LocalCache
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.pulentchallenge.musicfinder.network.SearchResult
import com.pulentchallenge.musicfinder.network.api.SearchService

class SearchRepository(
    private val searchService: SearchService,
    private val localCache: LocalCache
) {
    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }

    fun search(query: String): SearchResult {
        Log.d("Repository", "New query: $query")

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        // Get data source factory from the local cache
        val dataSourceFactory = localCache.tracksByName(query)

        // Construct the boundary callback
        val boundaryCallback = BoundaryCallback(query, searchService, localCache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        /*val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()*/

        val data = LivePagedListBuilder<Int, MusicTrackEntity>(
            dataSourceFactory,
            config
        ).setBoundaryCallback(boundaryCallback).build()

        // Get the network errors exposed by the boundary callback
        return SearchResult(data, networkErrors)
    }
}