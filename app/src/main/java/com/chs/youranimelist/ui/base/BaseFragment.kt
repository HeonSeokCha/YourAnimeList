package com.chs.youranimelist.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    internal var navigate: BaseNavigator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseNavigator) {
            navigate = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigate = null
    }
}

