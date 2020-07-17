package com.pulentchallenge.musicfinder.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.pulentchallenge.musicfinder.R
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.pulentchallenge.musicfinder.view.NavigationView
import com.pulentchallenge.musicfinder.view.fragment.PreviewFragment

class MainActivity : AppCompatActivity(), NavigationView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showPreview(musicTrackEntity: MusicTrackEntity) {
        val params = Bundle()
        params.putString(PreviewFragment.ART_WORK_URL, musicTrackEntity.artworkUrl100)
        params.putString(PreviewFragment.PREVIEW_URL, musicTrackEntity.previewUrl)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, PreviewFragment.newInstance(params))
            addToBackStack(null)
        }
    }
}