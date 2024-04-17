package com.example.music_app.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.music_app.R
import com.example.music_app.databinding.ItemAlbumBinding
import com.example.music_app.databinding.ItemArtistBinding
import com.example.music_app.models.Album
import com.example.music_app.models.Artist

class HomeAlbumAdapter(private val albums: List<Album>) :
    RecyclerView.Adapter<HomeAlbumAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    inner class ViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album) {
            binding.apply {
                with(album){
                    Glide.with(itemView)
                        .load(this.coverMedium)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewAlbum)
                    binding.txtTitle.text = this.title
                    binding.txtArtistName.text = this.artist.name
                }
            }
        }
    }
}