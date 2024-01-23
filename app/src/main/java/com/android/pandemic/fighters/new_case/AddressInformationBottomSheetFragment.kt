package com.android.pandemic.fighters.new_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.pandemic.fighters.base.BaseBottomSheetDialogFragment
import com.android.pandemic.fighters.databinding.FragmentAddressInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressInformationBottomSheetFragment(
    private val selectedLocation: String,
    private val dismissListener: () -> Unit,
    private val submitListener: () -> Unit
) : BaseBottomSheetDialogFragment<FragmentAddressInformationBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initView()
    }

    private fun initView() {
        binding.apply {
            ivCloseChooser.setOnClickListener {
                dismiss()
                dismissListener.invoke()
            }
            tvLocation.text = selectedLocation
            btnSubmit.setOnClickListener {
                submitListener.invoke()
                dismiss()
            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentAddressInformationBinding =
        FragmentAddressInformationBinding.inflate(inflater, container, false)

}