package com.android.pandemic.fighters.home.mapView

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.ResponseState
import com.android.pandemic.fighters.base.modifySuccessResponse
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.home.models.ReportedVirusCasesResponse
import com.android.pandemic.fighters.repositories.VirusRepository
import com.android.pandemic.fighters.utils.location.LocationEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    virusRepository: VirusRepository,
    locationEmitter: LocationEmitter
) : BaseViewModel() {

    private val _reportedCasesList = mutableSharedFlow<ResponseState<ReportedVirusCasesResponse>>()
    val reportedCasesList: SharedFlow<ResponseState<ReportedVirusCasesResponse>>
        get() = _reportedCasesList
    private val _list: MutableList<Document> = mutableListOf()

    private val _currentLocation = mutableSharedFlow<Location>()
    val currentLocation: SharedFlow<Location>
        get() = _currentLocation
    val list: List<Document> get() = _list

    init {
        viewModelScope.launch(Dispatchers.IO) {
            locationEmitter.getCurrentLocation().collect {
                _currentLocation.emit(it)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            _reportedCasesList.emit(
                virusRepository.getReportedVirusCases().modifySuccessResponse {
                    _list.apply {
                        clear()
                        addAll(it.documents)
                    }
                    it
                })
        }
    }
}