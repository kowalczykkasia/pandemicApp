package com.android.pandemic.fighters.home.mapView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.base.MapBaseFragment
import com.android.pandemic.fighters.databinding.FragmentHomeBinding
import com.android.pandemic.fighters.utils.extensions.handleResponseState
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : MapBaseFragment<FragmentHomeBinding>(), OnMapReadyCallback {

    private val viewModel by viewModels<HomeViewModel>()

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
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHomeListViewFragment())
            }
            btnReportNewCase.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReportNewCaseFragment())
            }
        }
    }

    private fun initViewModel() {
        viewModel.reportedCasesList.onEach {
            it.handleResponseState(successFun = {
                addMarkers()
            }, errorFun = {
                Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
            })
        }.launchIn(lifecycleScope)
        viewModel.currentLocation.onEach {
            setCurrentLocation(it)
        }.launchIn(lifecycleScope)
    }

    private fun addMarkers() {
        viewModel.list.forEach {
            addMarker(LatLng( it.fields.latitude.value, it.fields.longitude.value))
        }
    }

    override fun onMapReady(map: GoogleMap) {
        super.onMapReady(map)
        addMarkers()
        setCurrentLocation(viewModel.currentLocation.replayCache.firstOrNull(), moveCamera = true)
    }
}