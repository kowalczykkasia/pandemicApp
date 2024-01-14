package com.swifty.android.pandemicfightersportfolio.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.swifty.android.pandemicfightersportfolio.base.BaseFragment
import com.swifty.android.pandemicfightersportfolio.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}