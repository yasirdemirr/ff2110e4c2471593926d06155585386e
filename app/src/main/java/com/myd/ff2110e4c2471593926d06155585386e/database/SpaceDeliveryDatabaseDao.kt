package com.myd.ff2110e4c2471593926d06155585386e.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station

@Dao
interface SpaceDeliveryDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: Station)

    @Query("SELECT * FROM station_information_table")
    suspend fun getAllStation(): List<Station>

    @Query("DELETE FROM station_information_table")
    suspend fun clear()
}