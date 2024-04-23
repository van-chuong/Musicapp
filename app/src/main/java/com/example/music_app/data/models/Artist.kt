package com.example.music_app.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    @SerializedName("id")
    val id: String??,
    @SerializedName("name")
    val name: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("picture_small")
    val pictureSmall: String?,
    @SerializedName("picture_medium")
    val pictureMedium: String?,
    @SerializedName("picture_big")
    val pictureBig: String?,
    @SerializedName("picture_xl")
    val pictureXl: String?,
    @SerializedName("tracklist")
    val tracklist: String?,
) : Parcelable

data class ArtistResponse(
    val data: MutableList<Artist>,
)