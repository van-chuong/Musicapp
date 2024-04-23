package com.example.music_app.services

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.music_app.data.models.Track
import com.example.music_app.presentation.track.listener.OnTrackCompletionListener
import kotlin.random.Random

class TrackPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()
    private var playlist: MutableList<Track> = mutableListOf()
    private var currentTrackIndex: Int = -1
    private var isRepeatPlaylist: Boolean = false
    private var isShuffle: Boolean = false
    private val playedTracksIndices: MutableSet<Int> = mutableSetOf()
    private var trackCompletionListener: OnTrackCompletionListener? = null

    inner class LocalBinder : Binder() {
        fun getService(): TrackPlayerService = this@TrackPlayerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BGService", "Service started");
        if (mediaPlayer != null) {
            playTrack(null)
        } else {
            //ở đây có ý tưởng là lưu playlist vào local rôi load playlist ra
//            playTrack(playlist.first().preview)
        }
        return START_STICKY;
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    fun toggleRepeat() {
        isRepeatPlaylist = !isRepeatPlaylist
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
    }

    fun isRepeatPlaylist(): Boolean {
        return isRepeatPlaylist
    }

    fun isShuffle(): Boolean {
        return isShuffle
    }

    fun playTrack(url: String?) {
        playlist = PlaylistManager.getPlaylist().toMutableList()
        if (!url.isNullOrEmpty()) {
            if (mediaPlayer?.isPlaying == true) {
                stopTrack()
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            currentTrackIndex = playlist.indexOfFirst { it.preview == url }
        } else {
            mediaPlayer?.start()
        }
        trackCompletionListener?.onTrackCompleted()
        mediaPlayer?.setOnCompletionListener {
            playNextTrack()
        }
    }

    fun playNextTrack() {
        if (playlist.isNotEmpty()) {
            val nextTrack = if (isShuffle) {
                var randomIndex: Int
                do {
                    randomIndex = Random.nextInt(playlist.size)
                } while (playedTracksIndices.contains(randomIndex))
                playlist[randomIndex]
            } else {
                currentTrackIndex = (currentTrackIndex + 1) % playlist.size
                if (!isRepeatPlaylist && currentTrackIndex == 0) {
                    stopTrack()
                    playTrack(playlist.last().preview)
                    Toast.makeText(this, "No track to next", Toast.LENGTH_SHORT).show()
                    return
                }
                playlist[currentTrackIndex]
            }
            playTrack(nextTrack.preview)
            playedTracksIndices.add(currentTrackIndex)
            if (playedTracksIndices.size == playlist.size) {
                // Nếu đã phát tất cả các bài hát, reset danh sách
                Toast.makeText(
                    this,
                    "Playlist has finished playing, start playback",
                    Toast.LENGTH_SHORT
                ).show()
                playedTracksIndices.clear()
                return
            }
        } else {
            stopTrack()
        }
    }


    fun playPreviousTrack() {
        if (playlist.isNotEmpty()) {
            currentTrackIndex = if (currentTrackIndex == 0) {
                stopTrack()
                playTrack(playlist.first().preview)
                Toast.makeText(this, "No track to previous", Toast.LENGTH_SHORT).show()
                return
            } else {
                currentTrackIndex - 1
            }
            val previousTrack = playlist[currentTrackIndex]
            playTrack(previousTrack.preview)
        } else {
            stopTrack()
        }
    }

    fun pauseTrack() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer?.pause()
        }
    }

    fun isPlaying(): Boolean? {
        return mediaPlayer?.isPlaying
    }

    fun stopTrack() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun getCurrentTrackIndex(): Int {
        return currentTrackIndex
    }

    fun getTotalDuration(): Int {
        return mediaPlayer?.duration ?: 0
    }

    fun setOnTrackCompletionListener(listener: OnTrackCompletionListener?) {
        trackCompletionListener = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BGService", "Service destroyed");
        mediaPlayer?.release()
        mediaPlayer = null
    }
}