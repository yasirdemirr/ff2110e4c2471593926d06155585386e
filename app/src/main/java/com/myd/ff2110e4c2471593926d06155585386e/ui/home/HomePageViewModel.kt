package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.myd.ff2110e4c2471593926d06155585386e.data.model.StationItem
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.remote.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.resources.NetworkState
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val stationDataRepository: StationDataRepository
) : ViewModel() {

    fun getStations() = liveData(Dispatchers.IO) {
        emit(NetworkState.Loading)
        try {
            emit(NetworkState.Success(stationDataRepository.getStations()))
        } catch (e: Exception) {
            emit(NetworkState.Error(e))
        }
    }

    fun saveStationsToRoom(stations: List<StationItem>) {

    }
}