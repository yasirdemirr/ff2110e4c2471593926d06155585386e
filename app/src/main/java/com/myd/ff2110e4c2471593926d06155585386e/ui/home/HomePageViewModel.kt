package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myd.ff2110e4c2471593926d06155585386e.constant.ClickType
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.data.model.changeValueFromStation
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getDistance
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val repository: StationDataRepository
) : ViewModel() {
    private val station = MutableLiveData<MutableList<Station>>()
    val stationLiveData: LiveData<MutableList<Station>> = station

    private val selectedStationFavorite = MutableLiveData<Station>()
    val selectedStationFavoriteLiveData: LiveData<Station> = selectedStationFavorite

    private val goSelectedStationMutableLiveData = MutableLiveData<Station?>()
    val goSelectedStationLiveData: LiveData<Station?> =
        goSelectedStationMutableLiveData

    private val missionCompleted = MutableLiveData<String>()
    val missionCompletedLiveData: LiveData<String> = missionCompleted

    private var searchIsActive = false


    var vehicle = VehiclePreferences()
    var oldVehiclePref = VehiclePreferences()
    val ugsObservable = ObservableField<String>()
    val eusObservable = ObservableField<String>()
    val dsObservable = ObservableField<String>()
    val currentLocationNameObservable = ObservableField<String>()
    val vehicleNameObservable = ObservableField<String>()

    var durabilityTime = 0
    var isFirstTime = true

    fun getListOfStation() {
        viewModelScope.launch {
            station.value = repository.getStationsFromLocal()
        }
    }

    fun parseIntent(vehiclePreferences: VehiclePreferences) {
        if (isFirstTime) {
            oldVehiclePref = vehiclePreferences.copy()
            isFirstTime = false
        }
        setMenuItemValue(vehiclePreferences)
    }

    private fun setMenuItemValue(vehicle: VehiclePreferences) {
        this.vehicle = vehicle
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
                if (controlVehicleState(station)) {
                    updateStationStore(station)
                } else {
                    goBackToWorldForSupply()
                }
            }
            ClickType.ICON -> {
                updateClickedItemFavorite(station)
            }
        }
    }

    private fun goBackToWorldForSupply() {
        vehicle = oldVehiclePref
        updateDistanceFromWorld()
        missionCompleted.value = "İkmal için Dünya'ya dönülüyor..."
        setMenuItemValue(vehicle)
    }

    private fun updateDistanceFromWorld() {
        viewModelScope.launch {
            repository.getStationsFromLocal().forEach {
                it.distanceTimeCurrentLocation = vehicle.getDistance(it.coordinateX, it.coordinateY)
                repository.saveStationToLocal(it)
            }
            this@HomePageViewModel.station.value = repository.getStationsFromLocal()
        }
    }

    private fun controlVehicleState(station: Station) =
        ugsObservable.get()?.toInt()?.minus(station.need)!! >= 0 &&
                eusObservable.get()?.toInt()?.minus(station.distanceTimeCurrentLocation)!! >= 0 &&
                dsObservable.get()?.toInt()?.minus(durabilityTime.times(1000))!! >= 0


    private fun updateStationStore(station: Station) {
        vehicle.changeValueFromStation(station)
        durabilityTime.plus(station.distanceTimeCurrentLocation.times(1000))
        setMenuItemValue(vehicle)
        updateList(station)
    }

    private fun updateClickedItemFavorite(station: Station) {
        viewModelScope.launch {
            station.isFavorite = !station.isFavorite
            repository.update(station)
        }
        selectedStationFavorite.value = station
    }

    private fun updateList(station: Station) {
        viewModelScope.launch {
            repository.getStationsFromLocal().let {
                it.forEach { stationFromLis ->
                    if (stationFromLis.id == station.id) {
                        stationFromLis.stock = station.capacity
                        stationFromLis.need = 0
                        stationFromLis.capacityFull = true
                    }
                    stationFromLis.distanceTimeCurrentLocation =
                        vehicle.getDistance(
                            stationFromLis.coordinateX,
                            stationFromLis.coordinateY
                        )
                    stationFromLis.isCurrentLocation =
                        vehicle.currentLocationName == stationFromLis.name
                    repository.saveStationToLocal(stationFromLis)
                }
                if (repository.getStationsFromLocal().all { c -> c.capacityFull }) {
                    completedMission()
                }
                this@HomePageViewModel.station.value = repository.getStationsFromLocal()
            }

        }
    }

    private fun completedMission() {
        vehicle.currentLocationName = "Dünya"
        missionCompleted.value = "Dağıtım Görevi Tamamlandı. Dünyaya Dönülüyor..."
        setMenuItemValue(vehicle)
    }

    fun getFindItemByQuery(query: String) {
        viewModelScope.launch {
            goSelectedStationMutableLiveData.value =
                repository.getItem(query.toUpperCase() + "%")

        }
    }

    fun removeSearchData() {
        goSelectedStationMutableLiveData.value = null
    }
}




