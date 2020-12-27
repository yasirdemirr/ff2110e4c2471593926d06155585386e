package com.myd.ff2110e4c2471593926d06155585386e.data.repository

import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.locale.StationLocale
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.remote.StationRemote
import javax.inject.Inject

/**
 * created by yasirDemir
 */
class StationDataRepository @Inject constructor(
    private val stationRemote: StationRemote,
    private val stationLocale: StationLocale
) {
    suspend fun getStationsFromRemote() = stationRemote.getStations()

    suspend fun getStationsFromLocal() = stationLocale.getStations()

    suspend fun saveStationToLocal(station: Station) = stationLocale.insert(station)

    suspend fun clear() = stationLocale.clear()
}