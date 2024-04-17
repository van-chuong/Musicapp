package com.example.music_app.ui.fragments.home

import com.example.music_app.models.Album
import com.example.music_app.models.Artist
import com.example.music_app.models.Genre
import com.example.music_app.models.Playlist
import com.example.music_app.models.Track

interface HomeContract {
    interface View {
        fun showGenres(genres: List<Genre>)
        fun showAlbums(albums: List<Album>)
        fun showArtists(artists: List<Artist>)
        fun showTopTracks(tracks: List<Track>)
        fun showPopularPlaylists(playlists: List<Playlist>)
        fun showError(s: String)
    }

    interface Presenter {
        fun getGenres()
        fun getAlbums()
        fun getArtists()
        fun getTopTracks()
        fun getPopularPlaylists()
        fun detachView()
    }
}