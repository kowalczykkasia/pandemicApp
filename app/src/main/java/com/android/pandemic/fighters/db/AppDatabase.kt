package com.android.pandemic.fighters.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.pandemic.fighters.db.dao.ReportedCasesDao
import com.android.pandemic.fighters.home.models.Document
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        Document::class
    ],
    version = DB_VERSION, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun reportedCasesDao(): ReportedCasesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(
            context: Context,
            encryptedSharedPrefsClass: EncryptedSharedPrefsClass
        ): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, encryptedSharedPrefsClass).also { instance = it }
            }
        }

        private fun buildDatabase(
            context: Context,
            encryptedSharedPrefsClass: EncryptedSharedPrefsClass
        ): AppDatabase {
            val factory = SupportFactory(
                SQLiteDatabase.getBytes(
                    encryptedSharedPrefsClass.getDBEncryptionPhrase().toCharArray()
                )
            )
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}