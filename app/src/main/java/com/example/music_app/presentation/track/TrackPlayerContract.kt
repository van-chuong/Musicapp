package com.example.music_app.presentation.track

import android.content.Intent
import com.example.music_app.presentation.base.BasePresenter
import com.example.music_app.data.models.Track

interface TrackPlayerContract {
    interface View {
        fun showTrack(position: Int)
        fun showListTracks(tracks: MutableList<Track>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getTrack(intent: Intent)
        fun getListTracks()
    }
}