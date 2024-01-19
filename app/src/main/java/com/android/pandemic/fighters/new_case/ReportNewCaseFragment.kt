package com.android.pandemic.fighters.new_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.pandemic.fighters.base.BaseFragment
import com.android.pandemic.fighters.databinding.FragmentReportVirusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportNewCaseFragment : BaseFragment<FragmentReportVirusBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.apply {
            ivBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentReportVirusBinding = FragmentReportVirusBinding.inflate(inflater, container, false)

}