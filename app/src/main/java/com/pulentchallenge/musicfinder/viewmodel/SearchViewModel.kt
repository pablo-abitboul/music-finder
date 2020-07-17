package com.pulentchallenge.musicfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.pulentchallenge.musicfinder.network.SearchResult
import com.pulentchallenge.musicfinder.repository.SearchRepository

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    private val queryLiveData = MutableLiveData<String>()
    private val searchResult: LiveData<SearchResult> = Transformations.map(queryLiveData) {
        searchRepository.search(it)
    }

    val musicTracks: LiveData<PagedList<MusicTrackEntity>> =
        Transformations.switchMap(searchResult) { it -> it.data }
    val networkError: LiveData<String> = Transformations.switchMap(searchResult) { it ->
        it.networkError
    }

    fun search(queryString: String) {
        queryLiveData.postValue(queryString)
    }
}