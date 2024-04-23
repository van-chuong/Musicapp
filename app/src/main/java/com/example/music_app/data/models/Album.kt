package com.example.music_app.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("cover_small")
    val coverSmall: String?,
    @SerializedName("cover_medium")
    val coverMedium: String?,
    @SerializedName("cover_big")
    val coverBig: String?,
    @SerializedName("cover_xl")
    val coverXl: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("tracklist")
    val trackList: String?,
    @SerializedName("artist")
    val artist: Artist?
): Parcelable
data class AlbumResponse(
    val data: MutableList<Album>,
)

