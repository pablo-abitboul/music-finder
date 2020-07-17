package com.pulentchallenge.musicfinder.network.api

import com.pulentchallenge.musicfinder.network.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search")
    fun getSearchResults(
        @Query("term") searchTerm: CharSequence?,
        @Query("mediaType") entityType: String,
        @Query("limit") count: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Call<SearchResponse>
}