package com.android.pandemic.fighters.new_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.pandemic.fighters.base.BaseFragment
import com.android.pandemic.fighters.databinding.FragmentReportVirusBinding
import com.android.pandemic.fighters.new_case.models.Result
import com.android.pandemic.fighters.utils.SELECTED_LOCATION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportNewCaseFragment : BaseFragment<FragmentReportVirusBinding>() {

    private var selectedAddress: Result? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.apply {
            ivBack.setOnClickListener { findNavController().popBackStack() }
            btnEdit.setOnClickListener {
                findNavController().navigate(ReportNewCaseFragmentDirections.actionReportNewCaseFragmentToMapAddressSelectionFragment(selectedAddress))
            }
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Result>(
                SELECTED_LOCATION
            )?.observe(viewLifecycleOwner) {
                selectedAddress = it
                tvChosenLocation.text = it.formattedAddress
                btnAddNewCase.setActive()
            }
            btnAddNewCase.setOnClickListener {
                Toast.makeText(context, "Added new case - feature coming soon", Toast.LENGTH_SHORT).show() //todo
            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentReportVirusBinding = FragmentReportVirusBinding.inflate(inflater, container, false)

}