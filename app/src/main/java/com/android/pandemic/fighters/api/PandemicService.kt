package com.android.pandemic.fighters.api

import com.android.pandemic.fighters.home.models.ReportedVirusCasesResponse
import retrofit2.http.GET

interface PandemicService {

    @GET("reporting")
    suspend fun getReportedVirusCases(): ReportedVirusCasesResponse
}