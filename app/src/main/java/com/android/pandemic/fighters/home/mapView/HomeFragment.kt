package com.android.pandemic.fighters.home.mapView

import android.location.Location
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
import com.android.pandemic.fighters.databinding.FragmentHomeBinding
import com.android.pandemic.fighters.utils.DEFAULT_ZOOM
import com.android.pandemic.fighters.utils.extensions.generateBitmapDescriptorFromRes
import com.android.pandemic.fighters.utils.extensions.handleResponseState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnMapReadyCallback {

    private val viewModel by viewModels<HomeViewModel>()
    private var map: GoogleMap? = null
    private var currentLocationMarker: Marker? = null

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

    private fun setCurrentLocation(location: Location? = null, moveCamera: Boolean = false) {
        val lastKnownLocation = location ?: viewModel.currentLocation.replayCache.firstOrNull()
        map?.apply {
            lastKnownLocation?.let { location ->
                currentLocationMarker?.remove() ?: kotlin.run {
                    moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), DEFAULT_ZOOM))
                }
                currentLocationMarker = addMarker(createMarker(location.latitude, location.longitude, R.drawable.ic_location_pin))
                if(moveCamera) moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), DEFAULT_ZOOM))
            }
        }
    }

    private fun addMarkers() {
        viewModel.list.forEach {
            map?.addMarker(
                createMarker(
                    it.fields.latitude.value, it.fields.longitude.value
                )
            )
        }
    }

    private fun createMarker(lat: Double, long: Double, icon: Int = R.drawable.ic_pin) =
        MarkerOptions().position(LatLng(lat, long)).apply {
            icon(requireContext().generateBitmapDescriptorFromRes(icon))
        }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        addMarkers()
        setCurrentLocation(moveCamera = true)
    }
}