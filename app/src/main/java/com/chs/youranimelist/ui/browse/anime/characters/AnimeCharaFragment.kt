package com.chs.youranimelist.ui.browse.anime.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeCharaBinding
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.main.MainViewModel

class AnimeCharaFragment(private val charaList: List<AnimeDetailQuery.CharactersNode?>) : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeCharaBinding? = null
    private val binding get() = _binding!!
    private lateinit var charaAdapter: AnimeCharaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeCharaBinding.inflate(inflater,container,false)
        viewModel = MainViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter = AnimeCharaAdapter(charaList, clickListener = { charaId ->

            })
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(this@AnimeCharaFragment.context,3)
            this.addItemDecoration(SpacesItemDecoration(3,50,true))
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}