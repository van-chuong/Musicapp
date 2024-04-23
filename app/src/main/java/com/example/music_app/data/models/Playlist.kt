package com.example.music_app.data.models

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    @SerializedName("tracklist")
    val tracklist: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("tracks")
    val tracks: List<Track>,
)
data class PlaylistResponse(
    val data: MutableList<Playlist>
)
