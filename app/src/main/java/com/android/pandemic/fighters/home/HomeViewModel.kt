package com.android.pandemic.fighters.home

import androidx.lifecycle.viewModelScope
import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.DataState
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.home.models.ReportedVirusCasesResponse
import com.android.pandemic.fighters.repositories.VirusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    virusRepository: VirusRepository
) : BaseViewModel() {

    private val _reportedCasesList = mutableSharedFlow<DataState<ReportedVirusCasesResponse>>()
    val reportedCasesList: SharedFlow<DataState<ReportedVirusCasesResponse>>
        get() = _reportedCasesList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _reportedCasesList.emit(virusRepository.getReportedVirusCases())
        }
    }
}