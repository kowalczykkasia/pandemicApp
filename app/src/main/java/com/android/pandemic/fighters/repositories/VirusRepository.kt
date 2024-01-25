package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.base.handleApiResponse
import com.android.pandemic.fighters.db.dao.ReportedCasesDao
import com.android.pandemic.fighters.home.models.Document
import com.android.pandemic.fighters.utils.extensions.handleResponseState

class VirusRepository(
    private val pandemicService: PandemicService,
    private val reportedCasesDao: ReportedCasesDao
) {

    suspend fun getReportedVirusCases() = reportedCasesDao.getReportedCases()
    suspend fun updateData() =
        handleApiResponse {
            pandemicService.getReportedVirusCases()
        }.handleResponseState(successFun = {
            reportedCasesDao.deleteAllCases()
            reportedCasesDao.insertNewCases(it.documents)
        })

    suspend fun reportVirusCase(requestBody: Document) =
        handleApiResponse { pandemicService.reportVirusCase(requestBody) }.apply {
            handleResponseState(
                successFun = {
                    reportedCasesDao.insertNewCase(it)
                })
        }
}