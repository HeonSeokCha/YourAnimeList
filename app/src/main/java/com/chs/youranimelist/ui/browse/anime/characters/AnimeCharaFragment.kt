package com.chs.youranimelist.ui.browse.anime.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.util.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeCharaBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.browse.anime.AnimeDetailFragmentDirections
import com.chs.youranimelist.util.Constant

class AnimeCharaFragment : Fragment() {

    private var _binding: FragmentAnimeCharaBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { AnimeRepository() }
    private lateinit var charaAdapter: AnimeCharaAdapter
    private lateinit var viewModel: AnimeCharaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeCharaViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeCharaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.getAnimeCharacter(arguments?.getInt(Constant.TARGET_ID)!!)
        getCharacters()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun getCharacters() {
        viewModel.animeCharacterResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.shimmerAnimeChara.root.isVisible = true
                ResponseState.SUCCESS -> {
                    it.data?.media?.characters?.charactersNode?.forEach { animeChara ->
                        viewModel.animeCharacterList.add(animeChara!!)
                    }
                    charaAdapter.notifyDataSetChanged()
                    binding.rvAnimeChara.isVisible = true
                    binding.shimmerAnimeChara.root.isVisible = false
                }
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    binding.shimmerAnimeChara.root.isVisible = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter =
                AnimeCharaAdapter(viewModel.animeCharacterList) { charaId ->
                    val action =
                        AnimeDetailFragmentDirections.actionAnimeDetailFragmentToCharacterFragment(
                            charaId
                        )
                    findNavController().navigate(action)
                }
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(this@AnimeCharaFragment.context, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 50, true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeChara.adapter = null
        _binding = null
    }
}