package com.myd.ff2110e4c2471593926d06155585386e.data.repository.locale

import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.database.SpaceDeliveryDataBase
import javax.inject.Inject

class StationLocale @Inject constructor(private val spaceDeliveryDataBase: SpaceDeliveryDataBase) {
    suspend fun getStations() = spaceDeliveryDataBase.projectDao().getAllStation()

    suspend fun insert(station: Station) = spaceDeliveryDataBase.projectDao().insert(station)

    suspend fun clear() = spaceDeliveryDataBase.projectDao().clear()

    suspend fun update(station: Station) = spaceDeliveryDataBase.projectDao().update(station)
}