package com.example.music_app.presentation.search

import com.example.music_app.data.models.GenreResponse
import com.example.music_app.data.models.TrackResponse
import com.example.music_app.data.source.remote.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter : SearchContract.Presenter {
    private var view: SearchContract.View? = null
    override fun getGenres() {
        // Gọi API để lấy dữ liệu thể loại
        APIClient.instance.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                // Xử lý dữ liệu trả về
                val genreResponse = response.body()
                // Hiển thị dữ liệu lên View
                genreResponse?.data?.let { view?.onGetGenresSuccess(it) }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Failed to load genres: ${t.message}"))
            }
        })
    }

    override fun getListTracksByGenre(genre: String) {
        APIClient.instance.searchTracks("genre:$genre").enqueue(object : Callback<TrackResponse> {
            override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                // Xử lý dữ liệu trả về
                val trackResponse = response.body()
                // Hiển thị dữ liệu lên View
                trackResponse?.data?.let { view?.onGetListTracksByGenreSuccess(it) }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Failed to load tracks by genre: ${t.message}"))
            }
        })
    }

    override fun loadMoreTrack(genre: String, index: Int) {
        APIClient.instance.searchTracks("genre:$genre&index=$index").enqueue(object : Callback<TrackResponse> {
            override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                // Xử lý dữ liệu trả về
                val trackResponse = response.body()
                // Hiển thị dữ liệu lên View
                trackResponse?.data?.let { view?.onGetListTracksByGenreSuccess(it) }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                // Xử lý khi gặp lỗi
                view?.onError(Exception("Failed to load tracks by genre: ${t.message}"))
            }
        })
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: SearchContract.View?) {
        this.view = view
    }

    override fun destroyView() {
        this.view = null
    }
}