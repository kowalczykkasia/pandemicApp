package com.android.pandemic.fighters.new_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.base.BaseFragment
import com.android.pandemic.fighters.databinding.FragmentReportVirusBinding
import com.android.pandemic.fighters.new_case.models.Result
import com.android.pandemic.fighters.utils.SELECTED_LOCATION
import com.android.pandemic.fighters.utils.extensions.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReportNewCaseFragment : BaseFragment<FragmentReportVirusBinding>() {

    private var selectedAddress: Result? = null
    private val viewModel by viewModels<ReportNewCaseViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView(){
        binding.apply {
            ivBack.setOnClickListener { findNavController().popBackStack() }
            btnEdit.setOnClickListener {
                navigate(ReportNewCaseFragmentDirections.actionReportNewCaseFragmentToMapAddressSelectionFragment(selectedAddress))
            }
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Result>(
                SELECTED_LOCATION
            )?.observe(viewLifecycleOwner) {
                selectedAddress = it
                tvChosenLocation.text = it.formattedAddress
                btnAddNewCase.setActive()
            }
            btnAddNewCase.setOnClickListener {
                btnAddNewCase.setLoading()
                viewModel.reportVirusCase(etDescription.text.toString(), selectedAddress)
            }
        }
    }

    private fun initViewModel() {
        viewModel.error.onEach {
            binding.btnAddNewCase.setActive()
        }.launchIn(lifecycleScope)
        viewModel.response.onEach {
            findNavController().popBackStack()
            Toast.makeText(context, getString(R.string.successfully_reported), Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentReportVirusBinding = FragmentReportVirusBinding.inflate(inflater, container, false)

}