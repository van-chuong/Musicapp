package com.example.music_app.ui.activities.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.music_app.R
import com.example.music_app.databinding.ActivityMainBinding
import com.example.music_app.ui.fragments.favourite.FavouriteFragment
import com.example.music_app.ui.fragments.home.HomeFragment
import com.example.music_app.ui.fragments.profile.ProfileFragment
import com.example.music_app.ui.fragments.search.SearchFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    // Khai báo biến binding để sử dụng ViewBinding
    private lateinit var binding: ActivityMainBinding
    private var presenter: MainPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Khởi tạo presenter
        presenter = MainPresenter(this)
        // Hiển thị fragment Home khi vào MainActivity
        if (savedInstanceState == null) {
            val initialMenuItem = binding.bottomNavigation.menu.findItem(R.id.homePage)
            presenter?.handleNavigationItemSelected(initialMenuItem)
        }
        // Xử lý sự kiện khi bấm vào item trong bottom navigation view
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            presenter?.handleNavigationItemSelected(menuItem)
            true
        }
    }

    override fun showPage(menuItem: MenuItem) {
        val fragment = when (menuItem.itemId) {
            R.id.homePage -> HomeFragment()
            R.id.searchPage -> SearchFragment()
            R.id.favoritePage -> FavouriteFragment()
            R.id.profilePage -> ProfileFragment()
            else -> return
        }
        replaceFragment(fragment)
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }


}