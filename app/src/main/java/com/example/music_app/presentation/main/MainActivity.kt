package com.example.music_app.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.music_app.R
import com.example.music_app.databinding.ActivityMainBinding
import com.example.music_app.services.TrackPlayerService
import com.example.music_app.presentation.favourite.FavouriteFragment
import com.example.music_app.presentation.home.HomeFragment
import com.example.music_app.presentation.profile.ProfileFragment
import com.example.music_app.presentation.search.SearchFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    // Khai báo biến binding để sử dụng ViewBinding
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val presenter: MainPresenter by lazy {
        MainPresenter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Hiển thị fragment Home khi vào MainActivity
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
        // Xử lý sự kiện khi bấm vào item trong bottom navigation view
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.homePage -> HomeFragment()
                R.id.searchPage -> SearchFragment()
                R.id.favoritePage -> FavouriteFragment()
                R.id.profilePage -> ProfileFragment()
                else -> HomeFragment()
            }
            if(menuItem.itemId != binding.fragmentContainer.id){
                replaceFragment(fragment)
            }
            true
        }
        startService(Intent(this,TrackPlayerService::class.java))
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@MainActivity)
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroyView()
    }

}