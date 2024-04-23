package com.example.music_app.presentation.search.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.music_app.R
import com.example.music_app.data.models.Genre
import com.example.music_app.databinding.ItemGenreBinding

class SearchGenreAdapter : RecyclerView.Adapter<SearchGenreAdapter.ViewHolder>() {
    private val genres = mutableListOf<Genre>()
    private var listener: ((Genre) -> Unit)? = null
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            registerItemViewHolderListener {
                listener?.let { func -> func(genres[it]) }
            }
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position],position == selectedItemPosition)
    }

    fun addGenre(items: MutableList<Genre>?) {
        items?.let {
            genres.addAll(it)
            notifyItemRangeInserted(this.genres.size - it.size, it.size)
        }
    }

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre, isSelected: Boolean) {
            binding.apply {
                txtGenreName.text = genre.name
                if (isSelected) {
                    root.setBackgroundResource(R.drawable.genre_item_selected_bg)
                } else {
                    root.setBackgroundResource(R.drawable.genre_item_bg)
                }
            }
        }
        fun registerItemViewHolderListener(listener: (Int) -> Unit) {
            binding.root.setOnClickListener {
                listener(adapterPosition)
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
            }
        }
    }

    fun setOnClickItemListener(listener: ((Genre) -> Unit)?) {
        this.listener = listener
    }
}