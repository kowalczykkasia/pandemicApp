package com.android.pandemic.fighters.new_case

import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.ResponseState
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.home.models.DoubleValue
import com.android.pandemic.fighters.home.models.Fields
import com.android.pandemic.fighters.home.models.StringValue
import com.android.pandemic.fighters.new_case.models.Result
import com.android.pandemic.fighters.repositories.VirusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportNewCaseViewModel @Inject constructor(
    private val virusRepository: VirusRepository
) : BaseViewModel() {

    private val _response =
        mutableSharedFlow<ResponseState<Document>>()
    val response: SharedFlow<ResponseState<Document>>
        get() = _response

    fun reportVirusCase(description: String, location: Result?) {
        launch(Dispatchers.IO) {
            _response.emit(ResponseState.Loading)
            _response.emit(virusRepository.reportVirusCase(
                Document(
                    Fields(
                        StringValue(description),
                        StringValue(location?.formattedAddress ?: ""),
                        DoubleValue(location?.geometry?.location?.lat ?: 0.0),
                        DoubleValue(location?.geometry?.location?.lng ?: 0.0),
                        StringValue(System.currentTimeMillis().toString())
                    ), ""
                )
            ))
        }
    }
}