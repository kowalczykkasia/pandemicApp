package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.base.handleApiResponse

class VirusRepository(
    private val pandemicService: PandemicService
) {

    suspend fun getReportedVirusCases() = handleApiResponse { pandemicService.getReportedVirusCases() }
}