package com.example.music_app.presentation.home

import com.example.music_app.data.models.Album
import com.example.music_app.data.models.Artist
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.models.Track
import com.example.music_app.presentation.base.BasePresenter

interface HomeContract {
    interface View {
        fun onGetAlbumsSuccess(albums: MutableList<Album>)
        fun onGetArtistsSuccess(artists: MutableList<Artist>)
        fun onGetTopTracksSuccess(tracks: MutableList<Track>)
        fun onGetPopularPlaylistsSuccess(playlists: MutableList<Playlist>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getAlbums()
        fun getArtists()
        fun getTopTracks()
        fun getPopularPlaylists()
    }
}