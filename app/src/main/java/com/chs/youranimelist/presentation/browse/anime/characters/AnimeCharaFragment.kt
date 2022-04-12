package com.chs.youranimelist.presentation.browse.anime.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.util.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentAnimeCharaBinding
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailFragmentArgs
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeCharaFragment : Fragment() {

    private var _binding: FragmentAnimeCharaBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailFragmentArgs by navArgs()
    private lateinit var charaAdapter: AnimeCharaAdapter
    private val viewModel: AnimeCharaViewModel by viewModels()

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
        viewModel.getAnimeCharacter(args.id)
        getCharacters()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun getCharacters() {
        viewModel.animeCharacterResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    binding.shimmerAnimeChara.root.isVisible = true
                }
                is NetworkState.Success -> {
                    it.data?.media?.characters?.charactersNode?.forEach { animeChara ->
                        viewModel.animeCharacterList.add(animeChara!!)
                    }
                    charaAdapter.notifyDataSetChanged()
                    binding.rvAnimeChara.isVisible = true
                    binding.shimmerAnimeChara.root.isVisible = false
                }
                is NetworkState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.shimmerAnimeChara.root.isVisible = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimeChara.apply {
            charaAdapter =
                AnimeCharaAdapter(viewModel.animeCharacterList) { charaId ->
                    val action =
                        AnimeDetailFragmentDirections.actionAnimeDetailToCharacter(
                            charaId
                        )
                    findNavController().navigate(action)
                }
            this.setHasFixedSize(true)
            this.adapter = charaAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 3)
            this.addItemDecoration(SpacesItemDecoration(3, 50, true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvAnimeChara.adapter = null
        _binding = null
    }
}