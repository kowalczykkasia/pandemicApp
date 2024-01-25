package com.android.pandemic.fighters.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pandemic.fighters.home.models.Document
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportedCasesDao {

    @Query("SELECT * FROM reported_cases")
    fun getReportedCases() : Flow<List<Document>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCase(newDocument: Document)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCases(list: List<Document>)

    @Query("DELETE FROM reported_cases")
    fun deleteAllCases()

}