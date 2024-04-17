package com.example.music_app.ui.activities.main

import android.view.MenuItem

interface MainContract {
    interface View {
        fun showPage(menuItem: MenuItem)
    }

    interface Presenter {
        fun handleNavigationItemSelected(menuItem: MenuItem)
        fun detachView()
    }
}