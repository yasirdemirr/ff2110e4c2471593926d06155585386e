package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getCapacityAndDurabilty
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getSpeed
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val repository: StationDataRepository
) : ViewModel() {
    var x = Station()
    val station = MutableLiveData<List<Station>>()

    init {
        getListOfStation()
    }

    private fun getListOfStation() {
        viewModelScope.launch {
            station.value = repository.getStationsFromLocal()
        }
    }

    val ugsObservable = ObservableField<String>()
    val eusObservable = ObservableField<String>()
    val dsObservable = ObservableField<String>()

    fun parseIntent(vehiclePreferences: VehiclePreferences) {
        ugsObservable.set(vehiclePreferences.capacity.getCapacityAndDurabilty())
        eusObservable.set(vehiclePreferences.speed.getSpeed())
        dsObservable.set(vehiclePreferences.durability.getCapacityAndDurabilty())
    }

}


