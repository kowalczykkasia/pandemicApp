package com.android.pandemic.fighters.api

import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.home.models.ReportedVirusCasesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PandemicService {

    @GET("reporting")
    suspend fun getReportedVirusCases(): ReportedVirusCasesResponse

    @POST("reporting")
    suspend fun reportVirusCase(
        @Body document: Document
    ): ReportedVirusCasesResponse
}