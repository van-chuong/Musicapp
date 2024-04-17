package com.example.music_app.ui.fragments.home

import android.util.Log
import com.example.music_app.models.AlbumResponse
import com.example.music_app.models.ArtistResponse
import com.example.music_app.models.GenreResponse
import com.example.music_app.models.PlaylistResponse
import com.example.music_app.models.TrackResponse
import com.example.music_app.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(private var contract: HomeContract.View?) : HomeContract.Presenter {
    override fun getGenres() {
        // Gọi API để lấy dữ liệu thể loại
        APIClient.instance.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                // Xử lý dữ liệu trả về
                val genreResponse = response.body()
                // Hiển thị dữ liệu lên View
                contract?.showGenres(genreResponse?.data ?: emptyList())
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                contract?.showError("Failed to load genres: ${t.message}")
            }
        })
    }

    override fun getAlbums() {
        APIClient.instance.getAlbums().enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                // Xử lý dữ liệu trả về
                val albumResponse = response.body()
                // Hiển thị dữ liệu lên View
                contract?.showAlbums(albumResponse?.data ?: emptyList())
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                contract?.showError("Failed to load album: ${t.message}")
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
                contract?.showArtists(artistResponse?.data ?: emptyList())
            }

            override fun onFailure(call: Call<ArtistResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                contract?.showError("Failed to load artist: ${t.message}")
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
                contract?.showTopTracks(trackResponse?.data ?: emptyList())
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                contract?.showError("Failed to load track: ${t.message}")
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
                contract?.showPopularPlaylists(trackResponse?.data ?: emptyList())
            }

            override fun onFailure(call: Call<PlaylistResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                contract?.showError("Failed to load track: ${t.message}")
            }
        })
    }


    override fun detachView() {
        contract = null
    }
}