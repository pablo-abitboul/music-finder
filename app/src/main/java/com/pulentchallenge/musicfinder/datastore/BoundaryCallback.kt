package com.pulentchallenge.musicfinder.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.pulentchallenge.musicfinder.network.api.SearchService
import com.pulentchallenge.musicfinder.network.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoundaryCallback(
    private val query: String,
    private val searchService: SearchService,
    private val localCache: LocalCache
) : PagedList.BoundaryCallback<MusicTrackEntity>() {

    private val _networkErrors = MutableLiveData<String>()
    private val helper = PagingRequestHelper(localCache.getExecutor())

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        //1
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            getSearchResults(
                null,
                { musicTracks ->
                localCache.insert(musicTracks) {
                }
            }, {
            })
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: MusicTrackEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            getSearchResults(itemAtEnd,
                { musicTracks ->
                localCache.insert(musicTracks) {
                }
            }, {
            })
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val ENTITY_TYPE = "music"
    }

    private fun getSearchResults(
        itemAtEnd: MusicTrackEntity?,
        onSuccess: (musicTracks: List<MusicTrackEntity>) -> Unit,
        onError: (error: String) -> Unit
    ) {
        //Log.d(TAG, "query: $query, page: $page, itemsPerPage: $itemsPerPage")

        searchService.getSearchResults(query, ENTITY_TYPE, NETWORK_PAGE_SIZE, after = itemAtEnd?.trackName).enqueue(
            object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>?, t: Throwable) {
                    //Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                    call: Call<SearchResponse>?,
                    response: Response<SearchResponse>
                ) {
                    //Log.d(TAG, "got a response $response")
                    if (response.isSuccessful) {
                        val musicTracks = response.body()?.musicTrackModels ?: emptyList()
                        onSuccess(musicTracks)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
        )
    }

}