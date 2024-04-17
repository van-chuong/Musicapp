package com.example.music_app.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music_app.adapters.HomeAlbumAdapter
import com.example.music_app.adapters.HomeArtistAdapter
import com.example.music_app.adapters.HomePlaylistAdapter
import com.example.music_app.adapters.HomeTrackAdapter
import com.example.music_app.databinding.FragmentHomeBinding
import com.example.music_app.models.Album
import com.example.music_app.models.Artist
import com.example.music_app.models.Genre
import com.example.music_app.models.Playlist
import com.example.music_app.models.Track

class HomeFragment : Fragment(), HomeContract.View {
    private var binding: FragmentHomeBinding? = null
    private var homePresenter: HomePresenter? = null
    private var homeArtistAdapter: HomeArtistAdapter? = null
    private var homePlaylistAdapter: HomePlaylistAdapter? = null
    private var homeAlbumAdapter: HomeAlbumAdapter? = null
    private var homeTrackAdapter: HomeTrackAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePresenter = HomePresenter(this)
//        homePresenter?.getGenres()
        homePresenter?.getArtists()
        homePresenter?.getPopularPlaylists()
        homePresenter?.getAlbums()
        homePresenter?.getTopTracks()
    }


    override fun onDestroy() {
        super.onDestroy()
        homePresenter?.detachView()
        homePresenter = null
    }

    override fun showGenres(genres: List<Genre>) {
        for (genre in genres) {
            Log.d("Genre", genre.toString())
        }
    }

    override fun showAlbums(albums: List<Album>) {
        setupAlbumRecyclerView(albums)
    }

    override fun showArtists(artists: List<Artist>) {
        setupArtistRecyclerView(artists)
    }

    override fun showTopTracks(tracks: List<Track>) {
        setupTrackRecyclerView(tracks)
    }

    override fun showPopularPlaylists(playlists: List<Playlist>) {
        setupPlaylistRecyclerView(playlists)
    }

    override fun showError(s: String) {
        Log.d("Error", s)
    }

    private fun setupArtistRecyclerView(artists: List<Artist>) {
        // Khởi tạo và thiết lập adapter cho RecyclerView
        homeArtistAdapter = HomeArtistAdapter(artists) // Khởi tạo adapter trước
        binding?.rvArtist?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeArtistAdapter
            adapter = homeArtistAdapter
        }
    }

    private fun setupPlaylistRecyclerView(playlists: List<Playlist>) {
        // Khởi tạo và thiết lập adapter cho RecyclerView
        homePlaylistAdapter = HomePlaylistAdapter(playlists) // Khởi tạo adapter trước
        binding?.rvPlaylist?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homePlaylistAdapter
            adapter = homePlaylistAdapter
        }
    }

    private fun setupAlbumRecyclerView(albums: List<Album>) {
        // Khởi tạo và thiết lập adapter cho RecyclerView
        homeAlbumAdapter = HomeAlbumAdapter(albums) // Khởi tạo adapter trước
        binding?.rvAlbum?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            homeAlbumAdapter
            adapter = homeAlbumAdapter
        }
    }

    private fun setupTrackRecyclerView(tracks: List<Track>) {
        // Khởi tạo và thiết lập adapter cho RecyclerView
        homeTrackAdapter = HomeTrackAdapter(tracks) // Khởi tạo adapter trước
        binding?.rvTopTrack?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            homeTrackAdapter
            adapter = homeTrackAdapter
        }
    }

}