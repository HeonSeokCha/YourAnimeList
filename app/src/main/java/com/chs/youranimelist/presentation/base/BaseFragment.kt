package com.chs.youranimelist.presentation.base

import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
    }
}