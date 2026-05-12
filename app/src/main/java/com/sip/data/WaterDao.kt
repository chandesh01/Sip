package com.sip.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert
    suspend fun insertEntry(
        entry: WaterEntry
    )

    @Delete
    suspend fun deleteEntry(
        entry: WaterEntry
    )

    /*
    ---------------------------------------------------
    RAW ENTRIES
    ---------------------------------------------------
    */

    @Query(
        """
        SELECT *
        FROM water_entries
        ORDER BY timestamp DESC
        """
    )
    fun getAllEntries(): Flow<List<WaterEntry>>

    /*
    ---------------------------------------------------
    TOTAL BETWEEN
    ---------------------------------------------------
    */

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0)
        FROM water_entries
        WHERE timestamp BETWEEN :start AND :end
        """
    )
    fun getTotalBetween(
        start: Long,
        end: Long
    ): Flow<Int>
}