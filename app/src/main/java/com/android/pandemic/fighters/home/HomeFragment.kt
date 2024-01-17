package com.android.pandemic.fighters.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.base.BaseFragment
import com.android.pandemic.fighters.databinding.FragmentHomeBinding
import com.android.pandemic.fighters.utils.generateBitmapDescriptorFromRes
import com.android.pandemic.fighters.utils.handleResponseState
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnMapReadyCallback {

    private val viewModel by viewModels<HomeViewModel>()
    private var map: GoogleMap? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.mapView.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@HomeFragment)
        }
        initViewModel()
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            ivListView.setOnClickListener {
                //todo navigate 
            }
        }
    }

    private fun initViewModel() {
        viewModel.reportedCasesList.onEach {
            it.handleResponseState(successFun = {
                it.documents.forEach {
                    map?.addMarker(
                        createMarker(
                            it.fields.latitude.value, it.fields.longitude.value
                        )
                    )
                }
            }, errorFun = {
                //todo show toast
            })
        }.launchIn(lifecycleScope)
    }

    private fun createMarker(lat: Double, long: Double) =
        MarkerOptions().position(LatLng(lat, long)).apply {
            icon(requireContext().generateBitmapDescriptorFromRes(R.drawable.ic_pin))
        }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }
}