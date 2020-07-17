package com.pulentchallenge.musicfinder.view

import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity

interface NavigationView {
    fun showPreview(musicTrackEntity: MusicTrackEntity)
}