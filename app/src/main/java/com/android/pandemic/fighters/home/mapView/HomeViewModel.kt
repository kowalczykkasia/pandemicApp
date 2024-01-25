package com.android.pandemic.fighters.home.mapView

import android.location.Location
import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.repositories.VirusRepository
import com.android.pandemic.fighters.utils.location.LocationEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    virusRepository: VirusRepository,
    locationEmitter: LocationEmitter
) : BaseViewModel() {

    private val _reportedCasesList = mutableSharedFlow<List<Document>>()
    val reportedCasesList: SharedFlow<List<Document>>
        get() = _reportedCasesList

    private val _map: MutableSet<Document> = mutableSetOf()

    private val _currentLocation = mutableSharedFlow<Location>()
    val currentLocation: SharedFlow<Location>
        get() = _currentLocation

    init {
        launch {
            locationEmitter.getCurrentLocation().collect {
                _currentLocation.emit(it)
            }
        }
        launch {
            virusRepository.getReportedVirusCases().collect {
                _map.apply {
                    clear()
                    addAll(it)
                }
                _reportedCasesList.emit(it)
            }
        }
    }
}