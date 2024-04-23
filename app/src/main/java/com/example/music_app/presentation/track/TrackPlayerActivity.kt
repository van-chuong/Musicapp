package com.example.music_app.presentation.track

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.data.models.Track
import com.example.music_app.databinding.ActivityTrackPlayerBinding
import com.example.music_app.presentation.track.listener.OnTrackCompletionListener
import com.example.music_app.services.TrackPlayerService
import com.example.music_app.shared.helper.DateTimeFormatHelper

class TrackPlayerActivity : AppCompatActivity(), TrackPlayerContract.View {
    // Khai báo biến binding để sử dụng ViewBinding
    private val binding: ActivityTrackPlayerBinding by lazy {
        ActivityTrackPlayerBinding.inflate(layoutInflater)
    }
    private val trackPlayerPresenter: TrackPlayerPresenter by lazy {
        TrackPlayerPresenter()
    }
    private var trackPlayerService: TrackPlayerService? = null
    private var track: Track? = null
    private var isBound = false
    private val handler: Handler = Handler()
    private var tracks: MutableList<Track>? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TrackPlayerService.LocalBinder
            trackPlayerService = binder.getService()
            isBound = true
            // Cái này phải mất thời gian sau khi onStart nên phải xử lý ở đây
            updateUIWhenServiceConnected()
            handleEventWhenServiceConnected()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            trackPlayerService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Kết nối với dịch vụ
        bindTrackPlayerService()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        trackPlayerPresenter.run {
            setView(this@TrackPlayerActivity)
            getListTracks()
            getTrack(intent)
        }
    }
    private fun handleEventWhenServiceConnected(){
        // Kiểm tra xem có track nào được nhận hay không
        if (track != null && isBound) {
            track?.preview?.let { player -> trackPlayerService?.playTrack(player) }
            updatePlayerUI()
            updateSeekBar()
        }
        // Đăng ký sự kiện phát xong và next, cập nnật ui
        trackPlayerService?.setOnTrackCompletionListener(object : OnTrackCompletionListener {
            override fun onTrackCompleted() {
                trackPlayerService?.getCurrentTrackIndex()?.let { newTrack ->
                    showTrack(newTrack)
                }
            }
        })
    }
    private fun updateUIWhenServiceConnected() {
        val colorResShuffle = if (trackPlayerService?.isShuffle()!!) R.color.colorPrimary else R.color.black
        val colorResRepeat= if (trackPlayerService?.isRepeatPlaylist()!!) R.color.colorPrimary else R.color.black
        binding.btnShuffle.setColorFilter(ContextCompat.getColor(this, colorResShuffle), PorterDuff.Mode.SRC_ATOP)
        binding.btnRepeat.setColorFilter(ContextCompat.getColor(this, colorResRepeat), PorterDuff.Mode.SRC_ATOP)
    }
    private fun handleEvent() {
        // Bắt sự kiện click để phát nhạc
        binding.btnPlay.setOnClickListener {
            if (trackPlayerService?.isPlaying() == true) {
                // Nếu nhạc đang phát, tạm dừng nó
                trackPlayerService?.pauseTrack()
            } else {
                trackPlayerService?.playTrack(null)
            }
            updatePlayerUI()
        }
        // Bắt sự kiện Next
        binding.btnNext.setOnClickListener {
            trackPlayerService?.playNextTrack()
            trackPlayerService?.getCurrentTrackIndex()?.let { newTrack ->
                showTrack(newTrack)
            }
        }
        // Bắt sự kiện Previous
        binding.btnPrevious.setOnClickListener {
            trackPlayerService?.playPreviousTrack()
            trackPlayerService?.getCurrentTrackIndex()?.let { newTrack ->
                showTrack(newTrack)
            }
        }
        // Bắt sự kiện Shuffle
        binding.btnShuffle.setOnClickListener{
            trackPlayerService?.toggleShuffle()
            updateUIWhenServiceConnected()
        }
        // Bắt sự kiện Repeat
        binding.btnRepeat.setOnClickListener{
            trackPlayerService?.toggleRepeat()
            updateUIWhenServiceConnected()
        }
        // Bắt sự kiện click back
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        // Thiết lập sự kiện thay đổi của SeekBar
        binding.seekBarPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Chỉ thay đổi tiến độ khi người dùng thực hiện thay đổi
                if (fromUser) {
                    trackPlayerService?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }


    private fun bindTrackPlayerService() {
        bindService(
            Intent(this, TrackPlayerService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }



    override fun showTrack(position: Int) {
        track = tracks?.get(position)
        binding.txtArtistName.text = track?.artist?.name
        binding.txtTrackTitle.text = track?.title
        binding.imgViewTrack.let {
            Glide.with(baseContext)
                .load(track?.album?.coverMedium)
                .placeholder(R.drawable.img_loading)
                .centerCrop()
                .into(it)
        }
    }

    override fun showListTracks(tracks: MutableList<Track>) {
        this.tracks = tracks
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(this, exception?.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun updatePlayerUI() {
        if (isBound) {
            if (trackPlayerService?.isPlaying() == true) {
                binding.btnPlay.setImageResource(R.drawable.baseline_pause_24)
            } else {
                binding.btnPlay.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        } else {
            Toast.makeText(this, "Service not connected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSeekBar() {
        val totalDuration = trackPlayerService?.getTotalDuration() ?: 0
        binding.txtDuration.text =
            DateTimeFormatHelper.milliSecondsToMinutesAndSeconds(totalDuration.toLong())
        binding.seekBarPlayer.max = totalDuration
        handler.postDelayed({
            if (isBound && trackPlayerService?.isPlaying() == true) {
                val progress = trackPlayerService?.getCurrentPosition() ?: 0
                binding.seekBarPlayer.progress = progress
                binding.txtCurrentTime.text =
                    DateTimeFormatHelper.milliSecondsToMinutesAndSeconds(progress.toLong())

            }
            updateSeekBar()
        }, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        trackPlayerPresenter.destroyView()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

