package com.example.music_app.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_app.data.models.Genre
import com.example.music_app.data.models.Track
import com.example.music_app.databinding.FragmentSearchBinding
import com.example.music_app.presentation.home.adapters.HomeTrackAdapter
import com.example.music_app.presentation.search.adapters.SearchGenreAdapter


class SearchFragment : Fragment(), SearchContract.View {
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    private val searchPresenter by lazy { SearchPresenter() }
    private val searchGenreAdapter by lazy { SearchGenreAdapter() }
    private val searchTrackAdapter by lazy { HomeTrackAdapter() }
    private var genre: Genre? = null
    private var tracks: MutableList<Track>? = mutableListOf()
    private var loading: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    private fun handleEvent() {
        // Xử lý sự kiện click và thể loại
        searchGenreAdapter.setOnClickItemListener {
            genre = it
            searchPresenter.getListTracksByGenre(it.name)
        }
        // Xử lý sự kiện khi scroll hết item
        binding.rvTracksByGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvTracksByGenre.layoutManager as LinearLayoutManager
                if(!loading && layoutManager.findLastVisibleItemPosition()  == tracks?.size!! - 1){
                    //Load more item
                }
            }
        })
    }

    private fun initViews() {
        searchPresenter.run {
            getGenres()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvGenre.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = searchGenreAdapter
        }
        binding.rvTracksByGenre.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchTrackAdapter
        }
    }

    override fun onGetGenresSuccess(genres: MutableList<Genre>) {
        genre = genres.first()
        searchGenreAdapter.addGenre(genres)
        searchPresenter.getListTracksByGenre(genres.first().name)
    }

    override fun onGetListTracksByGenreSuccess(tracks: MutableList<Track>) {
        searchTrackAdapter.updateTracks(tracks)
        this.tracks = tracks
    }

    override fun onGetMoreTracksSuccess(tracks: MutableList<Track>) {
        searchTrackAdapter.addTrack(tracks)
    }

    override fun onError(exception: Exception?) {
        Log.d("Exception", exception?.message.toString())
    }

    override fun onStart() {
        super.onStart()
        searchPresenter.setView(this@SearchFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.destroyView()
    }

}