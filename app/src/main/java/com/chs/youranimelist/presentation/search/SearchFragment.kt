package com.chs.youranimelist.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chs.youranimelist.R
import com.chs.youranimelist.databinding.FragmentSearchBinding
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.onQueryTextChanged
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchKeywordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabView()
        setHasOptionsMenu(true)
    }

    private fun initTabView() {
        val tabArr: List<String> = listOf(
            Constant.TARGET_ANIME, Constant.TARGET_MANGA, Constant.TARGET_CHARA
        )
        binding.viewPagerSearch.adapter = SearchViewPagerAdapter(this, tabArr)
        TabLayoutMediator(binding.searchTabLayout, binding.viewPagerSearch) { tab, position ->
            for (i in 0..position) {
                tab.text = tabArr[i]
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_lists, menu)
        val searchItem = menu.findItem(R.id.menu_list_search)
        (searchItem.actionView as SearchView).apply {
            this.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.onKeyWordChanged(query)
                    }
                    closeKeyboard()
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean = false
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun closeKeyboard() {
        if (requireActivity().currentFocus != null) {
            val inputMethodManager = requireActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken, 0
            )
        }
    }
}