package com.example.music_app.presentation.main

class MainPresenter : MainContract.Presenter {
    private var view: MainContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: MainContract.View?) {
        this.view = view
    }

    override fun destroyView() {
        this.view = null
    }

}