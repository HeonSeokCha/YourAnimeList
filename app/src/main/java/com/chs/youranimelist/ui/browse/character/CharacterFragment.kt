package com.chs.youranimelist.ui.browse.character

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.SpacesItemDecoration
import com.chs.youranimelist.databinding.FragmentCharacterBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.CharacterRepository
import com.chs.youranimelist.ui.base.BaseFragment

class CharacterFragment : BaseFragment() {
    private val repository by lazy { CharacterRepository() }
    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private lateinit var scrollPosition: Bundle
    private lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        viewModel = CharacterViewModel(repository)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollPosition = Bundle()
        initView()
        initClick()
    }

    private fun initView() {
        getCharaInfo(arguments?.getInt("id", 0).toInput())
    }

    private fun initClick() {
        binding.characterToolbars.setNavigationOnClickListener { activity?.finish() }
        binding.btnExpand.setOnClickListener {
            if (binding.txtCharacterDescriptionPreview.isVisible) {
                binding.txtCharacterDescriptionPreview.visibility = View.GONE
                binding.txtCharacterDescription.visibility = View.VISIBLE
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_up)
            } else {
                binding.txtCharacterDescriptionPreview.visibility = View.VISIBLE
                binding.txtCharacterDescription.visibility = View.GONE
                binding.btnExpand.setBackgroundResource(R.drawable.ic_arrow_down)
            }
        }
    }

    private fun getCharaInfo(charaId: Input<Int>) {
        viewModel.getCharaInfo(charaId).observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> {
                }
                ResponseState.SUCCESS -> {
                    binding.model = it.data
                    initRecyclerView(it.data!!.media!!.edges!!)
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

    private fun initRecyclerView(items: List<CharacterQuery.Edge?>) {
        binding.rvCharaAnimeSeries.apply {
            adapter = CharacterAnimeAdapter(items,
                clickListener = { id ->
                    this@CharacterFragment.navigate?.changeFragment("Media", id, true)
                }
            ).apply {
                this.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.ALLOW
            }
            layoutManager = GridLayoutManager(this@CharacterFragment.context, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}