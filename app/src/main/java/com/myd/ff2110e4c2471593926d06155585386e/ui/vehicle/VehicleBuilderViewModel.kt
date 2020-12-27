package com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.myd.ff2110e4c2471593926d06155585386e.constant.Digits.FIFTEEN
import com.myd.ff2110e4c2471593926d06155585386e.constant.Digits.FIFTEEN_STRING
import com.myd.ff2110e4c2471593926d06155585386e.constant.Digits.ZERO
import com.myd.ff2110e4c2471593926d06155585386e.constant.Digits.ZERO_STRING
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.StationDataRepository
import com.myd.ff2110e4c2471593926d06155585386e.extensions.getDistance
import com.myd.ff2110e4c2471593926d06155585386e.resources.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

class VehicleBuilderViewModel @Inject constructor(
    private val stationDataRepository: StationDataRepository
) : ViewModel() {
    var vehicle = VehiclePreferences()
    val pointObservable = ObservableField(FIFTEEN_STRING)

    init {
        clearTable()
    }

    /**
     * Tekrark giriş yapıldıgında
     * room db'nin temizlenmesi
     */
    private fun clearTable() {
        viewModelScope.launch {
            stationDataRepository.clear()
        }
    }

    /**
     * Api üzerinden alkınan istasyonları
     * çekilmesi
     */
    fun getStations() = liveData(Dispatchers.IO) {
        emit(NetworkState.Loading)
        try {
            emit(NetworkState.Success(stationDataRepository.getStationsFromRemote()))
        } catch (e: Exception) {
            emit(NetworkState.Error(e))
        }
    }

    /**
     * Api üzerinden alkınan istasyonları
     * local db kayıt edilir
     *  @param List<Station>
     */
    fun saveStationsToRoom(stations: MutableList<Station>) {
        viewModelScope.launch {
            vehicle.currentLocationName = stations.first().name
            stations.forEach {
                it.distanceTimeCurrentLocation = vehicle.getDistance(it.coordinateX, it.coordinateY)
                stationDataRepository.saveStationToLocal(it)
            }
        }
    }

    private fun controlNewValue(selectedPreferences: Int, progress: Int): Int {
        val newValue: Int?
        val lastSelectionMinusOldSelection = selectedPreferences.minus(progress).absoluteValue
        newValue =
            if (pointObservable.get()?.toInt()?.minus(lastSelectionMinusOldSelection)!! >= ZERO) {
                pointObservable.set(
                    pointObservable.get()?.toInt()?.minus(lastSelectionMinusOldSelection).toString()
                )
                progress
            } else {
                pointObservable.set(ZERO_STRING)
                if (FIFTEEN - (vehicle.capacity + vehicle.speed + vehicle.durability) == ZERO)
                    progress else selectedPreferences + 15 - (vehicle.capacity + vehicle.speed + vehicle.durability)
            }
        return newValue
    }

    /**
     * Seçilen son seçeneğe göre
     *Seekbarların ayarlanması
     */
    private fun checkTotallyPoint(progress: Int, selectedVehiclePreference: Int) =
        if (progress < selectedVehiclePreference) {
            pointObservable.set(
                pointObservable.get()?.toInt()
                    ?.plus(progress.minus(selectedVehiclePreference).absoluteValue)
                    .toString()
            )
            progress

        } else {
            if (pointObservable.get() == ZERO_STRING) {
                selectedVehiclePreference

            } else {
                controlNewValue(selectedVehiclePreference, progress)
            }
        }

    /**
     * Kullanıcının bütün değerleri
     * Doğru bir şekilde girdiği kontrolu
     */
    fun checkButtonEnableValue() =
        vehicle.capacity > 0 &&
                vehicle.durability > 0 &&
                vehicle.speed > 0 &&
                !vehicle.name.isNullOrEmpty() &&
                pointObservable.get() == ZERO_STRING

    /**
     * Vehicle Hızının seçilen değerlere
     * göre setlenmesi
     * @param progress
     */
    fun checkVehicleSpeed(progress: Int): Int {
        vehicle.speed = checkTotallyPoint(progress, vehicle.speed)
        return vehicle.speed
    }

    /**
     * Vehicle Kapasitesinin seçilen değerlere
     * göre setlenmesi
     * @param progress
     */
    fun checkVehicleCapacity(progress: Int): Int {
        vehicle.capacity = checkTotallyPoint(progress, vehicle.capacity)
        return vehicle.capacity
    }

    /**
     * Vehicle Dayanıklılığının seçilen değerlere
     * göre setlenmesi
     * @param progress
     */
    fun checkVehicleDurability(progress: Int): Int {
        vehicle.durability =
            checkTotallyPoint(progress, vehicle.durability)
        return vehicle.durability
    }

    /**
     * Kullanıcının girdiği değerelere
     * göre araç isminin setlenmesi
     * @param text
     */
    fun getVehicleName(text: CharSequence?) {
        vehicle.name = text.toString().trim()
    }
}