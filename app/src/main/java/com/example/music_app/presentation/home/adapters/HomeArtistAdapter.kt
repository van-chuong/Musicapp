package com.example.music_app.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.data.models.Album
import com.example.music_app.data.models.Artist
import com.example.music_app.databinding.ItemArtistBinding

class HomeArtistAdapter : RecyclerView.Adapter<HomeArtistAdapter.ViewHolder>() {
    private val artists = mutableListOf<Artist>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artists[position])
    }
    fun addArtist(items: MutableList<Artist>?) {
        items?.let {
            artists.addAll(it)
            notifyItemRangeInserted(this.artists.size - it.size, it.size)
        }
    }
    inner class ViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.apply {
                with(artist) {
                    Glide.with(itemView)
                        .load(this.picture)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewArtist)
                    binding.txtArtistName.text = this.name
                }
            }
        }
    }
}