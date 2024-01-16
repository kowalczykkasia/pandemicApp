package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.base.handleApiResponse
import com.android.pandemic.fighters.home.models.Document

class VirusRepository(
    private val pandemicService: PandemicService
) {

    suspend fun getReportedVirusCases() =
        handleApiResponse { pandemicService.getReportedVirusCases() }

    suspend fun reportVirusCase(requestBody: Document) =
        handleApiResponse { pandemicService.reportVirusCase(requestBody) }
}