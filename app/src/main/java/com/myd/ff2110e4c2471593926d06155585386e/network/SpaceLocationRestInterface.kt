package com.myd.ff2110e4c2471593926d06155585386e.network

import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import retrofit2.http.GET

interface SpaceLocationRestInterface {
    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun fetchStations(): List<Station>
}