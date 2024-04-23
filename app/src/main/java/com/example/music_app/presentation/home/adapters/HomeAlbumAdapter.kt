package com.example.music_app.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music_app.R
import com.example.music_app.data.models.Album
import com.example.music_app.databinding.ItemAlbumBinding

class HomeAlbumAdapter : RecyclerView.Adapter<HomeAlbumAdapter.ViewHolder>() {
    private val albums = mutableListOf<Album>()
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

    fun addAlbum(items: MutableList<Album>?) {
        items?.let {
            albums.addAll(it)
            notifyItemRangeInserted(this.albums.size - it.size, it.size)
        }
    }

    inner class ViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.apply {
                with(album) {
                    Glide.with(itemView)
                        .load(this.coverMedium)
                        .placeholder(R.drawable.img_loading)
                        .centerCrop()
                        .into(binding.imgViewAlbum)
                    binding.txtTitle.text = this.title
                    binding.txtArtistName.text = this.artist?.name
                }
            }
        }
    }
}