package com.android.pandemic.fighters.new_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.base.MapBaseFragment
import com.android.pandemic.fighters.databinding.FragmentMapAddressSelectionBinding
import com.android.pandemic.fighters.utils.DEFAULT_ZOOM
import com.android.pandemic.fighters.utils.SELECTED_LOCATION
import com.android.pandemic.fighters.utils.extensions.handleResponseState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MapAddressSelectionFragment : MapBaseFragment<FragmentMapAddressSelectionBinding>(), OnMapClickListener {

    private var bottomSheetFragment: AddressInformationBottomSheetFragment? = null
    private val viewModel by viewModels<MapAddressSelectionViewModel>()
    private val args by navArgs<MapAddressSelectionFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        binding.mapView.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MapAddressSelectionFragment)
        }
    }

    private fun initView() {
        binding.apply {
            ivBack.setOnClickListener { findNavController().popBackStack() }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.getLatLongOfAddress(query)
                    return false
                }
            })
        }
    }

    private fun initViewModel() {
        viewModel.currentLocation.onEach {
            setCurrentLocation(it, args.selectedAddress == null, false)
        }.launchIn(lifecycleScope)
        viewModel.formattedAddress.onEach {
            it.handleResponseState(successFun = {
                it.results.firstOrNull()?.let {
                    bottomSheetFragment = AddressInformationBottomSheetFragment(it.formattedAddress, dismissListener =  {
                        map?.clear()
                    }, submitListener = {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(SELECTED_LOCATION, it)
                        findNavController().popBackStack()
                    })
                    bottomSheetFragment?.show(parentFragmentManager, this::class.java.simpleName)
                } ?: kotlin.run {
                    Toast.makeText(context, getString(R.string.location_not_found), Toast.LENGTH_SHORT).show()
                }
            })
        }.launchIn(lifecycleScope)
        viewModel.geoAddress.onEach {
            it.handleResponseState(successFun = {
                it.results.firstOrNull()?.let {
                    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.geometry.location.lat, it.geometry.location.lng), DEFAULT_ZOOM))
                }
            })
        }.launchIn(lifecycleScope)
    }

    override fun onMapReady(map: GoogleMap) {
        super.onMapReady(map)
        map.setOnMapClickListener(this)
        args.selectedAddress?.let {
            map.addMarker(createMarker(LatLng(it.geometry.location.lat, it.geometry.location.lng), R.drawable.ic_location_pin))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.geometry.location.lat, it.geometry.location.lng), DEFAULT_ZOOM))
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentMapAddressSelectionBinding =
        FragmentMapAddressSelectionBinding.inflate(inflater, container, false)

    override fun onMapClick(latLng: LatLng) {
        map?.clear()
        map?.addMarker(createMarker(latLng, R.drawable.ic_location_pin))
        viewModel.getFormattedAddress(latLng)
    }

}