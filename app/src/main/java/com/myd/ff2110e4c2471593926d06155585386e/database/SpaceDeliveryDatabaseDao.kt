package com.myd.ff2110e4c2471593926d06155585386e.database

import androidx.room.*
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station

@Dao
interface SpaceDeliveryDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: Station)

    @Update
    suspend fun update(station: Station)

    @Query("SELECT * FROM station_information_table")
    suspend fun getAllStation(): MutableList<Station>

    @Query("DELETE FROM station_information_table")
    suspend fun clear()

    @Query("SELECT * from station_information_table where name LIKE :name")
    suspend fun getItems(name: String): Station
}