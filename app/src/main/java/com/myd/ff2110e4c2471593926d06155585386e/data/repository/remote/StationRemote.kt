package com.myd.ff2110e4c2471593926d06155585386e.data.repository.remote

import com.myd.ff2110e4c2471593926d06155585386e.network.SpaceLocationRestInterface
import javax.inject.Inject

class StationRemote @Inject constructor(private val stationRestInterface: SpaceLocationRestInterface) {
    suspend fun getStations() = stationRestInterface.fetchStations()
}