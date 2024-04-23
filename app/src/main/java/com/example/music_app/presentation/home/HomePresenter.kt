package com.example.music_app.presentation.home

import com.example.music_app.data.models.AlbumResponse
import com.example.music_app.data.models.ArtistResponse
import com.example.music_app.data.models.PlaylistResponse
import com.example.music_app.data.models.TrackResponse
import com.example.music_app.data.source.remote.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter : HomeContract.Presenter {
    private var view: HomeContract.View? = null
    override fun getAlbums() {
        APIClient.instance.getAlbums().enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                // Xử lý dữ liệu trả về
                val albumResponse = response.body()
                // Hiển thị dữ liệu lên View
                albumResponse?.data?.let { view?.onGetAlbumsSuccess(it) }
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Error: ${t.message}"))
            }
        })
    }

    override fun getArtists() {
        APIClient.instance.getArtists().enqueue(object : Callback<ArtistResponse> {
            override fun onResponse(
                call: Call<ArtistResponse>,
                response: Response<ArtistResponse>
            ) {
                // Xử lý dữ liệu trả về
                val artistResponse = response.body()
                // Hiển thị dữ liệu lên View
                artistResponse?.data?.let { view?.onGetArtistsSuccess(it) }
            }

            override fun onFailure(call: Call<ArtistResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Error: ${t.message}"))
            }
        })
    }

    override fun getTopTracks() {
        APIClient.instance.getTopTracks().enqueue(object : Callback<TrackResponse> {
            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {
                // Xử lý dữ liệu trả về
                val trackResponse = response.body()
                // Hiển thị dữ liệu lên View
                trackResponse?.data?.let { view?.onGetTopTracksSuccess(it) }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Error: ${t.message}"))
            }
        })
    }

    override fun getPopularPlaylists() {
        APIClient.instance.getPopularPlaylists().enqueue(object : Callback<PlaylistResponse> {
            override fun onResponse(
                call: Call<PlaylistResponse>,
                response: Response<PlaylistResponse>
            ) {
                // Xử lý dữ liệu trả về
                val trackResponse = response.body()
                // Hiển thị dữ liệu lên View
                trackResponse?.data?.let { view?.onGetPopularPlaylistsSuccess(it) }
            }

            override fun onFailure(call: Call<PlaylistResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Error: ${t.message}"))
            }
        })
    }


    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override fun destroyView() {
        this.view = null
    }
}