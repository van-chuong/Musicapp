package com.example.music_app.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.data.models.Artist
import com.example.music_app.data.models.Playlist
import com.example.music_app.databinding.ItemPlaylistBinding
import com.example.music_app.shared.helper.DateTimeFormatHelper

class HomePlaylistAdapter : RecyclerView.Adapter<HomePlaylistAdapter.ViewHolder>() {
    private val playlists = mutableListOf<Playlist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playlists[position])
    }
    fun addPlaylist(items: MutableList<Playlist>?) {
        items?.let {
            playlists.addAll(it)
            notifyItemRangeInserted(this.playlists.size - it.size, it.size)
        }
    }
    inner class ViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.apply {
                with(playlist) {
                    Glide.with(itemView)
                        .load(this.picture)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewPlaylist)
                    binding.txtTitle.text = this.title
                    binding.txtCreationDate.text =
                        DateTimeFormatHelper.formatDateTime(this.creationDate)
                }
            }
        }
    }
}