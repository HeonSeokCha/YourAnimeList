package com.chs.youranimelist.ui.browse.anime.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeCharaBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment

class AnimeCharaFragment() :
    BaseFragment() {
    private lateinit var viewModel: AnimeCharaViewModel
    private val repository by lazy { AnimeRepository() }
    private var _binding: FragmentAnimeCharaBinding? = null
    private val binding get() = _binding!!
    private lateinit var charaAdapter: AnimeCharaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeCharaBinding.inflate(inflater, container, false)
        viewModel = AnimeCharaViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.getAnimeCharacter(arguments?.getInt("id")!!)
        getCharacters()
    }

    private fun getCharacters() {
        viewModel.animeCharacterResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.progressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    it.data?.media?.characters?.charactersNode?.forEach { animeChara ->
                        viewModel.animeCharacterList.add(animeChara!!)
                    }
                    charaAdapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter =
                AnimeCharaAdapter(viewModel.animeCharacterList) { charaId ->
                    this@AnimeCharaFragment.navigate!!.changeFragment("CHARA", charaId)
                }
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(this@AnimeCharaFragment.context, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 50, true))
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