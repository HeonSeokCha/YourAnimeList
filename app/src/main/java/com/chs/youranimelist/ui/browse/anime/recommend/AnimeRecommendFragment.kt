package com.chs.youranimelist.ui.browse.anime.recommend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.databinding.FragmentAnimeRecommendBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment


class AnimeRecommendFragment : BaseFragment() {
    private var _binding: FragmentAnimeRecommendBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeRecommendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeRecommendBinding.inflate(inflater, container, false)
        viewModel = AnimeRecommendViewModel(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRecommendList(arguments?.getInt("id")!!)
    }

    private fun getRecommendList(animeId: Int) {
        viewModel.getRecommendList(animeId).observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> {
                }
                ResponseState.SUCCESS -> initRecyclerView(it.data!!)
                ResponseState.ERROR -> {
                    Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView(items: List<AnimeRecommendQuery.Edge?>) {
        binding.rvAnimeRecommend.apply {
            this.adapter = AnimeRecommendAdapter(
                items, clickListener = { id ->
                    this@AnimeRecommendFragment.navigate?.changeFragment("Media", id, true)
                }
            )
            this.layoutManager = LinearLayoutManager(this@AnimeRecommendFragment.context)
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