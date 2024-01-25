package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.db.dao.ReportedCasesDao
import com.android.pandemic.fighters.home.models.Document

class VirusRepository(
    private val pandemicService: PandemicService,
    private val reportedCasesDao: ReportedCasesDao
) {
    fun getReportedVirusCases() = reportedCasesDao.getReportedCases()

    suspend fun updateData() {
        val response = pandemicService.getReportedVirusCases()
        reportedCasesDao.deleteAllCases()
        reportedCasesDao.insertNewCases(response.documents)
    }

    suspend fun reportVirusCase(requestBody: Document): Document {
        val response = pandemicService.reportVirusCase(requestBody)
        reportedCasesDao.insertNewCase(response)
        return response
    }
}