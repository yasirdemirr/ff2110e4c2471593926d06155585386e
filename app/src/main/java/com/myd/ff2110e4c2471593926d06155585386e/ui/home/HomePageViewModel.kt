package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myd.ff2110e4c2471593926d06155585386e.constant.ClickType
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getDistance
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val repository: StationDataRepository
) : ViewModel() {
    private val station = MutableLiveData<MutableList<Station>>()
    val stationLiveData: LiveData<MutableList<Station>> = station

    private val selectedStation = MutableLiveData<Station>()
    val selectedStationLiveData: LiveData<Station> = selectedStation


    var vehicle = VehiclePreferences()
    val ugsObservable = ObservableField<String>()
    val eusObservable = ObservableField<String>()
    val dsObservable = ObservableField<String>()
    val currentLocationNameObservable = ObservableField<String>()
    val vehicleNameObservable = ObservableField<String>()


    fun getListOfStation() {
        viewModelScope.launch {
            station.value = repository.getStationsFromLocal()
        }
    }

    fun parseIntent(vehiclePreferences: VehiclePreferences) {
        vehicle = vehiclePreferences
        vehicle.let {
            ugsObservable.set(it.capacity.toString())
            eusObservable.set(it.speed.toString())
            dsObservable.set(it.durability.toString())
            currentLocationNameObservable.set(it.currentLocationName)
            vehicleNameObservable.set(it.name)
        }
    }

    fun travelBySelectedStation(station: Station, clickType: ClickType) {
        when (clickType) {
            ClickType.BUTTON -> {
                vehicle.currentLocationName = station.name
                vehicle.currentLocationX = station.coordinateX
                vehicle.currentLocationY = station.coordinateY
                vehicle.capacity = vehicle.capacity.minus(station.need)
                parseIntent(vehicle)
                updateList()
            }
            ClickType.ICON -> {
                updateClickedItemFavorite(station)
            }
        }
    }

    private fun updateClickedItemFavorite(station: Station) {
        viewModelScope.launch {
            station.isFavorite = !station.isFavorite
            repository.update(station)
        }
        selectedStation.value = station
    }

    private fun updateList() {
        viewModelScope.launch {
            repository.getStationsFromLocal().forEach {
                it.distanceTimeCurrentLocation = vehicle.getDistance(it.coordinateX, it.coordinateY)
                it.isCurrentLocation = vehicle.currentLocationName == it.name
                repository.saveStationToLocal(it)
            }
            station.value = repository.getStationsFromLocal()
        }
    }
}


