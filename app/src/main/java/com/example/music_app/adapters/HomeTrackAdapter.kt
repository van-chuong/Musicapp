package com.example.music_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.databinding.ItemPlaylistBinding
import com.example.music_app.databinding.ItemTrackBinding
import com.example.music_app.models.Playlist
import com.example.music_app.models.Track
import com.example.music_app.utils.helper.DateTimeFormatHelper

class HomeTrackAdapter(private val tracks: List<Track>) :
    RecyclerView.Adapter<HomeTrackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    inner class ViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(track: Track) {
            binding.apply {
                with(track){
                    Glide.with(itemView)
                        .load(this.album.coverMedium)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewTrack)
                    binding.txtTitle.text = this.titleShort
                    binding.txtArtistName.text = this.artist.name
                    binding.txtDuration.text = DateTimeFormatHelper.formatSecondsToMinutesAndSeconds(this.duration)
                }
            }
        }
    }
}