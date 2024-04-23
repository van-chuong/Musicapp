package com.example.music_app.services

import com.example.music_app.data.models.Track

object PlaylistManager {
    private val playlist: MutableList<Track> = mutableListOf()

    fun getPlaylist(): List<Track> {
        return playlist
    }

    fun addToPlaylist(track: Track) {
        playlist.add(track)
    }

    fun addToPlaylist(tracks: List<Track>) {
        for (track in tracks) {
            if (!playlist.any { it.id == track.id }) {
                playlist.add(track)
            }
        }
    }

    fun deleteAll() {
        playlist.clear()
    }
}
