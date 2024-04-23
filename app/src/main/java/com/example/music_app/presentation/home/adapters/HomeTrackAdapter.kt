package com.example.music_app.presentation.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.data.models.Track
import com.example.music_app.databinding.ItemTrackBinding
import com.example.music_app.shared.helper.DateTimeFormatHelper


class HomeTrackAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tracks = mutableListOf<Track>()
    var onItemClickListener: ((track: Track, position: Int) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bind(tracks[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(tracks[position], position)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun addTrack(items: MutableList<Track>?) {
        items?.let {
            tracks.addAll(it)
            notifyItemRangeInserted(this.tracks.size - it.size, it.size)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTracks(items: MutableList<Track>) {
        tracks.clear()
        tracks.addAll(items)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.apply {
                with(track) {
                    Glide.with(itemView)
                        .load(this.album?.coverMedium)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewTrack)
                    binding.txtTitle.text = this.titleShort
                    binding.txtArtistName.text = this.artist?.name
                    binding.txtDuration.text =
                        DateTimeFormatHelper.formatSecondsToMinutesAndSeconds(this.duration)
                }
            }
        }
    }
}