package com.sip.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    /*
    ---------------------------------------------------
    INSERT
    ---------------------------------------------------
    */

    @Insert
    suspend fun insertEntry(
        entry: WaterEntry
    )

    /*
    ---------------------------------------------------
    DELETE
    ---------------------------------------------------
    */

    @Delete
    suspend fun deleteEntry(
        entry: WaterEntry
    )

    /*
    ---------------------------------------------------
    ALL ENTRIES
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
    TOTAL BETWEEN DATES
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

    /*
    ---------------------------------------------------
    ENTRIES BETWEEN DATES
    ---------------------------------------------------
    */

    @Query(
        """
    SELECT *
    FROM water_entries
    WHERE timestamp BETWEEN :start AND :end
    ORDER BY timestamp ASC
    """
    )
    fun getEntriesBetween(
        start: Long,
        end: Long
    ): Flow<List<WaterEntry>>
}