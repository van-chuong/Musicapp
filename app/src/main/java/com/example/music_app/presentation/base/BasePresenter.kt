package com.example.music_app.presentation.base

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
    fun destroyView()
}
