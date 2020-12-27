package com.myd.ff2110e4c2471593926d06155585386e.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station


@Database(entities = [Station::class], version = 16, exportSchema = false)
abstract class SpaceDeliveryDataBase : RoomDatabase() {
    abstract fun projectDao(): SpaceDeliveryDatabaseDao
}