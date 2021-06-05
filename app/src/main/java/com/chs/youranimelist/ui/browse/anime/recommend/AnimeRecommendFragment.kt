package com.chs.youranimelist.ui.browse.anime.recommend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.youranimelist.AnimeRecommendQuery
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentAnimeRecommendBinding
import com.chs.youranimelist.network.ResponseState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.ui.base.BaseFragment
import com.chs.youranimelist.util.Constant


class AnimeRecommendFragment : BaseFragment(R.layout.fragment_anime_recommend) {
    private val binding: FragmentAnimeRecommendBinding by viewBinding()
    private val repository by lazy { AnimeRepository() }
    private lateinit var viewModel: AnimeRecommendViewModel
    private lateinit var animeRecommendAdapter: AnimeRecommendAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AnimeRecommendViewModel(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getRecommendList(arguments?.getInt("id")!!)
        getRecommendList()
    }

    private fun getRecommendList() {
        viewModel.animeRecommendResponse.observe(viewLifecycleOwner, {
            when (it.responseState) {
                ResponseState.LOADING -> binding.progressBar.isVisible = true
                ResponseState.SUCCESS -> {
                    it.data?.media?.recommendations?.edges?.forEach { recommend ->
                        viewModel.animeRecList.add(recommend)
                    }
                    animeRecommendAdapter.notifyDataSetChanged()
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
        binding.rvAnimeRecommend.apply {
            animeRecommendAdapter = AnimeRecommendAdapter(viewModel.animeRecList) { id, idMal ->
                this@AnimeRecommendFragment.navigate?.changeFragment(
                    Constant.TARGET_MEDIA,
                    id,
                    idMal,
                    true
                )
            }
            this.adapter = animeRecommendAdapter
            this.layoutManager = LinearLayoutManager(this@AnimeRecommendFragment.context)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}