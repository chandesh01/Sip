package com.sip.data

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WaterEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SipDatabase : RoomDatabase() {

    abstract fun waterDao(): WaterDao

    companion object {

        @Volatile
        private var INSTANCE: SipDatabase? = null

        fun getDatabase(
            context: Context
        ): SipDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SipDatabase::class.java,
                    "sip_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}