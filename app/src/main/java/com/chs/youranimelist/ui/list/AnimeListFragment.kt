package com.chs.youranimelist.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.ConvertDate
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeListBinding
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import com.chs.youranimelist.ui.browse.BrowseActivity
import com.chs.youranimelist.ui.main.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect


class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnimeListViewModel
    private val repository by lazy { AnimeListRepository() }
    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var sort:MediaSort
    private var page = 1
    private var mediaSeason: MediaSeason? = null
    private var seasonYear: Int? = null
    private var season: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        viewModel = AnimeListViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}