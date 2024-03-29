package com.android.pandemic.fighters.home.listView

import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.repositories.VirusRepository
import com.android.pandemic.fighters.utils.CLOSEST_LOCATION
import com.android.pandemic.fighters.utils.RECENT_DATE
import com.android.pandemic.fighters.utils.extensions.distanceTo
import com.android.pandemic.fighters.utils.location.LocationEmitter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeListViewViewModel @Inject constructor(
    virusRepository: VirusRepository,
    private val locationEmitter: LocationEmitter
) : BaseViewModel() {

    private val _reportedCasesList = mutableSharedFlow<List<Document>>()
    val reportedCasesList: SharedFlow<List<Document>>
        get() = _reportedCasesList

    init {
        launch {
            virusRepository.getReportedVirusCases().collect {
                _reportedCasesList.emit(sort(RECENT_DATE, it))
            }
        }
    }

    fun sortBy(sortCriteria: String, newList: List<Document>? = null) {
        val oldList = newList ?: (reportedCasesList.replayCache.firstOrNull()) ?: listOf()
        launch {
            _reportedCasesList.emit(sort(sortCriteria, oldList))
        }
    }

    private fun sort(sortCriteria: String, list: List<Document>): List<Document> {
        return when (sortCriteria) {
            CLOSEST_LOCATION -> {
                val lastKnownLocation =
                    locationEmitter.getCurrentLocation().replayCache.firstOrNull()
                val currentLocation =
                    LatLng(lastKnownLocation?.latitude ?: 0.0, lastKnownLocation?.longitude ?: 0.0)
                list.sortedBy {
                    LatLng(
                        it.fields.latitude.value,
                        it.fields.longitude.value
                    ).distanceTo(currentLocation)
                }
            }

            else -> list.sortedByDescending { it.fields.timestamp.value }
        }
    }
}