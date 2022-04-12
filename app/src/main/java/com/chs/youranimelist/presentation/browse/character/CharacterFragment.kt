package com.chs.youranimelist.presentation.browse.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.R
import com.chs.youranimelist.data.NetworkState
import com.chs.youranimelist.databinding.FragmentCharacterBinding
import com.chs.youranimelist.presentation.base.BaseFragment
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : BaseFragment() {
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private var animeAdapter: CharacterAnimeAdapter? = null
    private val args: CharacterFragmentArgs by navArgs()
    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharaInfo(args.id)
        checkCharaList()
        initRecyclerView()
        getCharaInfo()
        initClick()
    }

    private fun initClick() {
        binding.btnExpand.setOnClickListener {
            if (binding.txtCharacterDescriptionPreview.isVisible) {
                binding.txtCharacterDescriptionPreview.isVisible = false
                binding.txtCharacterDescription.isVisible = true
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_up)
            } else {
                binding.txtCharacterDescription.isVisible = false
                binding.txtCharacterDescriptionPreview.isVisible = true
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_down)
            }
        }
        binding.mediaSaveList.setOnClickListener {
            saveList()
        }
        binding.characterToolbars.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun getCharaInfo() {
        viewModel.characterDetailResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Success -> {
                    binding.model = it.data?.character
                    viewModel.charaDetail = it.data?.character
                    it.data?.character?.media?.edges?.forEach { anime ->
                        viewModel.characterAnimeList.add(anime)
                    }
                    animeAdapter?.notifyDataSetChanged()
                }
                is NetworkState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvCharaAnimeSeries.apply {
            animeAdapter = CharacterAnimeAdapter(viewModel.characterAnimeList) { id, idMal ->
                val action =
                    CharacterFragmentDirections.actionCharacterFragmentToAnimeDetailFragment(
                        id,
                        idMal
                    )
                findNavController().navigate(action)
            }
            this.setHasFixedSize(true)
            this.adapter = animeAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
        }
    }

    private fun saveList() {
        if (viewModel.charaDetail != null && viewModel.initCharaList == null) {
            viewModel.insertCharaList(viewModel.charaDetail!!)
        } else if (viewModel.initCharaList != null) {
            viewModel.deleteCharaList(viewModel.initCharaList!!)
            viewModel.initCharaList = null
        }
    }

    private fun checkCharaList() {
        viewModel.checkCharaList(args.id)
            .observe(viewLifecycleOwner) { charaInfo ->
                if (charaInfo != null && charaInfo.charaId == arguments?.getInt(Constant.TARGET_ID)!!) {
                    viewModel.initCharaList = charaInfo!!
                    binding.mediaSaveList.apply {
                        this.icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
                        this.text = "SAVED"
                    }
                } else {
                    binding.mediaSaveList.apply {
                        this.icon = null
                        this.text = "ADD MY LIST"
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.characterAnimeList.clear()
        binding.rvCharaAnimeSeries.adapter = null
        _binding = null
    }
}