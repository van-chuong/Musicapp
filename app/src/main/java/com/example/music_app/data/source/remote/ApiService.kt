package com.example.music_app.data.source.remote

import com.example.music_app.data.models.AlbumResponse
import com.example.music_app.data.models.ArtistResponse
import com.example.music_app.data.models.GenreResponse
import com.example.music_app.data.models.PlaylistResponse
import com.example.music_app.data.models.TrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("genre")
    fun getGenres(): Call<GenreResponse>

    @GET("chart/0/artists")
    fun getArtists(): Call<ArtistResponse>

    @GET("chart/0/albums")
    fun getAlbums(): Call<AlbumResponse>

    @GET("chart/0/tracks")
    fun getTopTracks(): Call<TrackResponse>

    @GET("chart/0/playlists")
    fun getPopularPlaylists(): Call<PlaylistResponse>

    @GET("search")
    fun searchTracks(@Query("q") query: String): Call<TrackResponse>
}