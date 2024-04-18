package com.example.music_app.ui.activities.track

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.music_app.R
import com.example.music_app.databinding.ActivityMainBinding

class TrackPlayerActivity : AppCompatActivity() {
    // Khai báo biến binding để sử dụng ViewBinding
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}