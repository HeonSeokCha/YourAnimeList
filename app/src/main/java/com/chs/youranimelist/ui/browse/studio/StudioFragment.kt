package com.chs.youranimelist.ui.browse.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.chs.youranimelist.databinding.FragmentStudioBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.StudioRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.SpacesItemDecoration

class StudioFragment : BaseFragment() {
    private var _binding: FragmentStudioBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { StudioRepository() }
    private val args: StudioFragmentArgs by navArgs()
    private var studioAnimeAdapter: StudioAnimeAdapter? = null
    private lateinit var viewModel: StudioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = StudioViewModel(repository)
        viewModel.studioId = args.studioId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStudio()
        initClick()
        initRecyclerView()
        initStudio()
    }

    private fun initStudio() {
        viewModel.studioResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.SUCCESS -> {
                    binding.model = it.data
                    it.data!!.media!!.edges!!.forEach { edge ->
                        viewModel.studioAnimeList.add(edge!!)
                    }
                    studioAnimeAdapter?.notifyDataSetChanged()
                }
                ResponseState.ERROR -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initClick() {
        binding.studioToolbars.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.txtStudioSort.setOnClickListener {
            AlertDialog.Builder(this.requireContext())
                .setItems(Constant.animeSortArray) { _, which ->
                    viewModel.selectsort = Constant.animeSortList[which]
                    binding.txtStudioSort.text = Constant.animeSortArray[which]
                    viewModel.refresh()
                    studioAnimeAdapter?.notifyDataSetChanged()
                }
                .show()
        }
    }

    private fun initRecyclerView() {
        binding.rvStudio.apply {
            studioAnimeAdapter = StudioAnimeAdapter(viewModel.studioAnimeList) { id, idMal ->
                val action =
                    StudioFragmentDirections.actionStudioFragmentToAnimeDetailFragment(
                        id,
                        idMal
                    )
                findNavController().navigate(action)
            }
            this.adapter = studioAnimeAdapter
            this.layoutManager = GridLayoutManager(this@StudioFragment.context, 3)
            this.addItemDecoration(SpacesItemDecoration(3, 8, true))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        studioAnimeAdapter = null
        viewModel.clear()
        _binding = null
    }
}