package com.pulentchallenge.musicfinder.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pulentchallenge.musicfinder.R
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_search_result.view.*

class MusicTrackItemAdapter(musicTrackItemListener: MusicTrackItemListener) :
    PagedListAdapter<MusicTrackEntity, MusicTrackItemAdapter.MusicTrackItemViewHolder>(
        MUSIC_TRACK_DIFF_UTIL
    ) {

    companion object {
        private val MUSIC_TRACK_DIFF_UTIL = object : DiffUtil.ItemCallback<MusicTrackEntity>() {
            override fun areItemsTheSame(
                oldItem: MusicTrackEntity,
                newItem: MusicTrackEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MusicTrackEntity,
                newItem: MusicTrackEntity
            ): Boolean =
                oldItem.trackName == newItem.trackName &&
                        oldItem.artistName == newItem.artistName &&
                        oldItem.collectionName == newItem.collectionName &&
                        oldItem.artworkUrl100 == newItem.artworkUrl100 &&
                        oldItem.previewUrl == newItem.previewUrl
        }
    }

    private var itemViewListener = musicTrackItemListener

    interface MusicTrackItemListener {
        fun onItemClicked(musicTrackModel: MusicTrackEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicTrackItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return MusicTrackItemViewHolder(inflatedView, itemViewListener)
    }

    override fun onBindViewHolder(holder: MusicTrackItemViewHolder, position: Int) {
        val itemResult = getItem(position)
        itemResult?.let {
            holder.bind(it)
        }
    }

    class MusicTrackItemViewHolder(
        private val musicTrackItemView: View,
        musicTrackItemListener: MusicTrackItemListener
    ) :
        RecyclerView.ViewHolder(musicTrackItemView), View.OnClickListener {
        private val itemViewListener = musicTrackItemListener
        private lateinit var musicTrackEntity: MusicTrackEntity

        init {
            musicTrackItemView.setOnClickListener(this)
        }

        fun bind(musicTrackModel: MusicTrackEntity) {
            musicTrackEntity = musicTrackModel
            musicTrackItemView.tv_artist_name.text = musicTrackEntity.artistName
            musicTrackItemView.tv_track_name.text = musicTrackEntity.trackName
            musicTrackItemView.tv_album_name.text = musicTrackEntity.collectionName
            Picasso.get().load(musicTrackEntity.artworkUrl100).into(musicTrackItemView.iv_album)
        }

        override fun onClick(v: View?) {
            itemViewListener.onItemClicked(musicTrackEntity)
        }
    }
}