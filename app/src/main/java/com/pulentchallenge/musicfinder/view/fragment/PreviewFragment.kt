package com.pulentchallenge.musicfinder.view.fragment

import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.pulentchallenge.musicfinder.databinding.FragmentPreviewBinding
import com.squareup.picasso.Picasso

class PreviewFragment : Fragment() {
    private lateinit var vBinding: FragmentPreviewBinding
    private var exoPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackState.Builder? = null
    private var mediaSession: MediaSession? = null

    private var artWorkUrl: String? = null
    private var previewUrl: String? = null

    companion object {

        const val ART_WORK_URL = "art_work_url"
        const val PREVIEW_URL = "preview_url"

        @JvmStatic
        fun newInstance(params: Bundle) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ART_WORK_URL, params.getString(ART_WORK_URL))
                    putString(PREVIEW_URL, params.getString(PREVIEW_URL))
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artWorkUrl = it.getString(ART_WORK_URL)
            previewUrl = it.getString(PREVIEW_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentPreviewBinding =
            FragmentPreviewBinding.inflate(inflater, container, false)
        vBinding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun initPlayer() {
        if (exoPlayer == null) {

            val trackSelector = DefaultTrackSelector()
            exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), trackSelector)
            vBinding.simpleExoPlayerView.player = exoPlayer

            val userAgent = Util.getUserAgent(requireContext(), "Exo")
            val mediaUri =
                Uri.parse(previewUrl)
            val mediaSource = ExtractorMediaSource(
                mediaUri,
                DefaultDataSourceFactory(requireContext(), userAgent),
                DefaultExtractorsFactory(),
                null,
                null
            )

            exoPlayer?.prepare(mediaSource)
            mediaSession = MediaSession(requireContext(), "ExoPlayer")

            playbackStateBuilder = PlaybackState.Builder()
            playbackStateBuilder?.setActions(
                PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PAUSE or
                        PlaybackState.ACTION_FAST_FORWARD
            )

            mediaSession?.setPlaybackState(playbackStateBuilder?.build())
            mediaSession?.isActive = true

            Picasso.get()
                .load(artWorkUrl)
                .into(vBinding.ivAlbum)
        }
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.release()
            exoPlayer = null
        }
    }
}