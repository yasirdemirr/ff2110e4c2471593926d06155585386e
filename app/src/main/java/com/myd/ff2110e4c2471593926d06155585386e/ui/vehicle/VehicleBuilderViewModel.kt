package com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.myd.ff2110e4c2471593926d06155585386e.data.model.StationItem
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.remote.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.resources.NetworkState
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.math.absoluteValue

class VehicleBuilderViewModel @Inject constructor(
    private val stationDataRepository: StationDataRepository
) : ViewModel() {

    var vehicle = VehiclePreferences()
    val pointObservable = ObservableField<String>("15")

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

    fun checkVehicleDurability(durability: Int): Int {
        vehicle.durability =
            checkTotallyPoint((durability), vehicle.durability)
        return vehicle.durability
    }


    private fun controlNewValue(durability: Int, point: Int): Int {
        val newValue: Int?
        val minusvalue = durability.minus(point).absoluteValue
        if (pointObservable.get()?.toInt()?.minus(minusvalue)!! >= 0) {
            pointObservable.set(pointObservable.get()?.toInt()?.minus(minusvalue).toString())
            newValue = point
        } else {
            pointObservable.set("0")
            newValue = if (15 - (vehicle.capacity + vehicle.speed + vehicle.durability) == 0)
                point else durability + 15 - (vehicle.capacity + vehicle.speed + vehicle.durability)
        }
        return newValue
    }

    private fun checkTotallyPoint(point: Int, vehicleDurability: Int) =
        if (point < vehicleDurability) {
            pointObservable.set(
                pointObservable.get()?.toInt()
                    ?.plus(point.minus(vehicleDurability).absoluteValue)
                    .toString()
            )
            point

        } else {
            if (pointObservable.get() == "0") {
                vehicleDurability

            } else {
                controlNewValue(vehicleDurability, point)
            }
        }

    fun checkButtonEnableValue() =
        vehicle.capacity > 0 &&
                vehicle.durability > 0 &&
                vehicle.speed > 0 &&
                !vehicle.name.isNullOrEmpty() &&
                pointObservable.get() == "0"

    fun checkVehicleSpeed(speed: Int): Int {
        vehicle.speed = checkTotallyPoint(speed, vehicle.speed)
        return vehicle.speed
    }

    fun checkVehicleCapacity(capacity: Int): Int {
        vehicle.capacity = checkTotallyPoint(capacity, vehicle.capacity)
        return vehicle.capacity
    }

    fun getVehicleName(text: CharSequence?) {
        vehicle.name = text.toString().trim()
    }
}