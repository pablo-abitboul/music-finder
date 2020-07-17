package com.pulentchallenge.musicfinder.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.pulentchallenge.musicfinder.databinding.FragmentSearchBinding
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity
import com.pulentchallenge.musicfinder.view.NavigationView
import com.pulentchallenge.musicfinder.view.adapter.MusicTrackItemAdapter
import com.pulentchallenge.musicfinder.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), MusicTrackItemAdapter.MusicTrackItemListener {
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var vBinding: FragmentSearchBinding
    private lateinit var searchAdapter: MusicTrackItemAdapter
    private var navigationView: NavigationView? = null

    private val searchResultsObserver = Observer<PagedList<MusicTrackEntity>> {
        searchAdapter.submitList(it)
        showResults(it?.size != 0)
    }

    private val networkErrorObserver = Observer<String> {
        Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentSearchBinding =
            FragmentSearchBinding.inflate(inflater, container, false)
        vBinding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchAdapter = MusicTrackItemAdapter(this)
        vBinding.rvSearchResults.layoutManager = linearLayoutManager
        vBinding.rvSearchResults.adapter = searchAdapter

        searchViewModel.musicTracks.observe(viewLifecycleOwner, searchResultsObserver)
        searchViewModel.networkError.observe(viewLifecycleOwner, networkErrorObserver)

        setUpInputListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            navigationView = context as NavigationView
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NavigationView")
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navigationView = null
    }

    override fun onItemClicked(musicTrackModel: MusicTrackEntity) {
        navigationView?.showPreview(musicTrackModel)
    }

    private fun setUpInputListener() {
        vBinding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateMusicTrackListFromInput()
                true
            } else {
                false
            }
        }
        vBinding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateMusicTrackListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateMusicTrackListFromInput() {
        vBinding.etSearch.text?.trim().let {
            if (it != null) {
                if (it.isNotEmpty()) {
                    vBinding.rvSearchResults.scrollToPosition(0)
                    searchViewModel.search(it.toString())
                    searchAdapter.submitList(null)
                }
            }
        }
    }

    private fun showResults(shouldShowResults: Boolean) {
        if (shouldShowResults) {
            vBinding.rvSearchResults.visibility = View.VISIBLE
            vBinding.tvNoResults.visibility = View.GONE
        } else {
            vBinding.rvSearchResults.visibility = View.GONE
            vBinding.tvNoResults.visibility = View.VISIBLE
        }
    }
}