package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getCapacityAndDurabilty
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getSpeed
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
) : ViewModel() {

    val ugsObservable = ObservableField<String>()
    val eusObservable = ObservableField<String>()
    val dsObservable = ObservableField<String>()

    fun parseIntent(vehiclePreferences: VehiclePreferences) {
        ugsObservable.set(vehiclePreferences.capacity.getCapacityAndDurabilty())
        eusObservable.set(vehiclePreferences.speed.getSpeed())
        dsObservable.set(vehiclePreferences.durability.getCapacityAndDurabilty())
    }

}


