package com.example.music_app.models

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    @SerializedName("duration")
    val duration: Long,
    @SerializedName("link")
    val link: String,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("album")
    val album: Album,
)
data class TrackResponse(
    val data: List<Track>
)

