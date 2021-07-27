package com.chs.youranimelist.ui.browse.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.R
import com.chs.youranimelist.util.SpacesItemDecoration
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.databinding.FragmentCharacterBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.CharacterRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.util.Constant

class CharacterFragment : BaseFragment() {
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { CharacterRepository() }
    private lateinit var viewModel: CharacterViewModel
    private var animeAdapter: CharacterAnimeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CharacterViewModel(repository, requireActivity().application)
    }

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
        checkCharaList()
        initRecyclerView()
        viewModel.getCharaInfo(arguments?.getInt(Constant.TARGET_ID).toInput())
        getCharaInfo()
        initClick()
        binding.lifecycleOwner = this
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
        viewModel.characterDetailResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.SUCCESS -> {
                    binding.model = it.data?.character
                    viewModel.charaDetail = it.data?.character
                    it.data?.character?.media?.edges?.forEach { anime ->
                        viewModel.characterAnimeList.add(anime)
                    }
                    animeAdapter?.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(
                        this@CharacterFragment.context,
                        it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
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
            this.adapter = animeAdapter
            this.layoutManager = GridLayoutManager(this@CharacterFragment.context, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
        }
    }

    private fun saveList() {
        if (viewModel.charaDetail != null && viewModel.initCharaList == null) {
            with(viewModel.charaDetail!!) {
                viewModel.insertCharaList(
                    Character(
                        charaId = this.id,
                        name = this.name?.full ?: "",
                        nativeName = this.name?.native_ ?: "",
                        image = this.image?.large ?: "",
                        favourites = this.favourites,
                    )
                )
            }
        } else if (viewModel.initCharaList != null) {
            viewModel.deleteCharaList(viewModel.initCharaList!!)
            viewModel.initCharaList = null
        }
    }

    private fun checkCharaList() {
        viewModel.checkCharaList(arguments?.getInt(Constant.TARGET_ID)!!)
            .observe(viewLifecycleOwner, {
                if (it.size == 1 && it[0].charaId == arguments?.getInt(Constant.TARGET_ID)!!) {
                    viewModel.initCharaList = it[0]
                    binding.mediaSaveList.apply {
                        val animatedDrawable =
                            ContextCompat.getDrawable(this.context!!, R.drawable.ic_check)!!
                        animatedDrawable.setBounds(0, 0, 50, 50)
                    }
                } else {
                    binding.mediaSaveList.apply {
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.characterAnimeList.clear()
        binding.rvCharaAnimeSeries.adapter = null
        _binding = null
    }
}