package com.example.music_app.models

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
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
    @SerializedName("radio")
    val radio: Boolean,
    @SerializedName("tracklist")
    val tracklist: String,
)

data class ArtistResponse(
    val data: List<Artist>,
)