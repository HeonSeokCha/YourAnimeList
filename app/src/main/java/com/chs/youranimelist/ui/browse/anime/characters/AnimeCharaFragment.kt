package com.chs.youranimelist.ui.browse.anime.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeCharaBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.ui.main.MainViewModel

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
        getCharacters(arguments?.getInt("id")!!)
    }

    private fun getCharacters(animeId: Int) {
        viewModel.getAnimeCharacter(animeId)
        viewModel.animeCharacterResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> {
                }
                ResponseState.SUCCESS -> {
                    it.data?.media?.characters?.charactersNode?.forEach { animeChara ->
                        viewModel.animeCharacterList.add(animeChara!!)
                    }
                    charaAdapter.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter =
                AnimeCharaAdapter(viewModel.animeCharacterList, clickListener = { charaId ->
                    this@AnimeCharaFragment.navigate!!.changeFragment("CHARA", charaId)
                })
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