package com.example.music_app.ui.activities.main

import android.view.MenuItem
import com.example.music_app.R
import com.example.music_app.ui.fragments.favourite.FavouriteFragment
import com.example.music_app.ui.fragments.home.HomeFragment
import com.example.music_app.ui.fragments.profile.ProfileFragment
import com.example.music_app.ui.fragments.search.SearchFragment

class MainPresenter(private var contract: MainContract.View?) : MainContract.Presenter {
    override fun handleNavigationItemSelected(menuItem: MenuItem) {
        contract?.showPage(menuItem)
    }

    override fun detachView() {
        contract = null
    }

}