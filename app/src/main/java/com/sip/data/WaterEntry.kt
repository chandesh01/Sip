package com.sip.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_entries")
data class WaterEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Int,

    val timestamp: Long
)