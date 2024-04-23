package com.example.music_app.presentation.search

import com.example.music_app.data.models.Genre
import com.example.music_app.data.models.Track
import com.example.music_app.presentation.base.BasePresenter

interface SearchContract {
    interface View {
        fun onGetGenresSuccess(genres: MutableList<Genre>)
        fun onGetListTracksByGenreSuccess(tracks: MutableList<Track>)
        fun onGetMoreTracksSuccess(tracks: MutableList<Track>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getGenres()
        fun getListTracksByGenre(genre: String)
        fun loadMoreTrack(genre: String, index: Int)
    }
}