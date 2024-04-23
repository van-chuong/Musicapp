package com.example.music_app.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music_app.data.models.Album
import com.example.music_app.data.models.Artist
import com.example.music_app.data.models.Playlist
import com.example.music_app.data.models.Track
import com.example.music_app.databinding.FragmentHomeBinding
import com.example.music_app.presentation.home.adapters.HomeAlbumAdapter
import com.example.music_app.presentation.home.adapters.HomeArtistAdapter
import com.example.music_app.presentation.home.adapters.HomePlaylistAdapter
import com.example.music_app.presentation.home.adapters.HomeTrackAdapter
import com.example.music_app.presentation.track.TrackPlayerActivity
import com.example.music_app.services.PlaylistManager
import com.example.music_app.services.TrackPlayerService

class HomeFragment : Fragment(), HomeContract.View {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val homePresenter by lazy {
        HomePresenter()
    }
    private val homeArtistAdapter: HomeArtistAdapter by lazy {
        HomeArtistAdapter()
    }
    private val homePlaylistAdapter: HomePlaylistAdapter by lazy {
        HomePlaylistAdapter()
    }
    private val homeAlbumAdapter: HomeAlbumAdapter by lazy {
        HomeAlbumAdapter()
    }
    private val homeTrackAdapter: HomeTrackAdapter by lazy {
        HomeTrackAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        homePresenter.run {
            getArtists()
            getPopularPlaylists()
            getAlbums()
            getTopTracks()
        }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        homePresenter.setView(this@HomeFragment)
    }

    override fun onGetAlbumsSuccess(albums: MutableList<Album>) {
        homeAlbumAdapter.addAlbum(albums)
    }

    override fun onGetArtistsSuccess(artists: MutableList<Artist>) {
        homeArtistAdapter.addArtist(artists)
    }

    override fun onGetTopTracksSuccess(tracks: MutableList<Track>) {
        homeTrackAdapter.addTrack(tracks)
        PlaylistManager.addToPlaylist(tracks)
    }

    override fun onGetPopularPlaylistsSuccess(playlists: MutableList<Playlist>) {
        homePlaylistAdapter.addPlaylist(playlists)
    }

    override fun onError(exception: Exception?) {
        Log.d("Exception", exception?.message.toString())
    }

    private fun initRecyclerView() {
        setupArtistRecyclerView()
        setupAlbumRecyclerView()
        setupPlaylistRecyclerView()
        setupTrackRecyclerView()
    }

    private fun setupArtistRecyclerView() {
        binding.rvArtist.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeArtistAdapter
        }
    }

    private fun setupPlaylistRecyclerView() {
        binding.rvPlaylist.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homePlaylistAdapter
        }
    }

    private fun setupAlbumRecyclerView() {
        binding.rvAlbum.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeAlbumAdapter
        }
    }

    private fun setupTrackRecyclerView() {
        binding.rvTopTrack.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = homeTrackAdapter
            homeTrackAdapter.onItemClickListener = { track, position ->
                val intent = Intent(requireContext(), TrackPlayerActivity::class.java)
                intent.putExtra("track", track)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy")
        homePresenter.destroyView()
    }
}