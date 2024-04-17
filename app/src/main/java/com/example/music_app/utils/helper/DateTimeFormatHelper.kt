package com.example.music_app.utils.helper

import android.annotation.SuppressLint
import androidx.core.net.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
object DateTimeFormatHelper {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val outputFormat = SimpleDateFormat("dd-MM-yyyy")
    fun formatDateTime(inputDateString: String): String {
        return try {
            val date = inputFormat.parse(inputDateString)
            outputFormat.format(date as Date)
        } catch (e: ParseException) {
            e.printStackTrace()
            "Invalid Date"
        }
    }
    fun formatSecondsToMinutesAndSeconds(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
