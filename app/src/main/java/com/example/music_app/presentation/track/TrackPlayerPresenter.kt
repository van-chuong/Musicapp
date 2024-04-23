package com.example.music_app.presentation.track

import android.content.Intent
import android.os.Build
import com.example.music_app.data.models.Track
import com.example.music_app.services.PlaylistManager

class TrackPlayerPresenter : TrackPlayerContract.Presenter {
    private var view: TrackPlayerContract.View? = null
    override fun getTrack(intent: Intent) {
        // Nhận đối tượng Track từ Intent
        val track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("track", Track::class.java)
        } else {
            intent.getParcelableExtra("track")
        }
        val position = intent.getIntExtra("position", 0)
        // Hiển thị thông tin Track
        if (track != null) {
            view?.showTrack(position)
        } else {
            view?.onError(Exception("Error: No track data found"))
        }
    }

    override fun getListTracks() {
        val listTracks = PlaylistManager.getPlaylist().toMutableList()
        if (listTracks.isNotEmpty()) {
            view?.showListTracks(listTracks)
        } else {
            view?.onError(Exception("Error: No tracks data found"))
        }
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: TrackPlayerContract.View?) {
        this.view = view
    }

    override fun destroyView() {
        this.view = null
    }

}