package com.myd.ff2110e4c2471593926d06155585386e.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.data.repository.StationDataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * createdx yasirDemir
 */
class FavoriteViewModel @Inject constructor(
    private val repository: StationDataRepository
) : ViewModel() {

    private val favoriteStations = MutableLiveData<MutableList<Station>>()
    val favoriteStationsLiveData: LiveData<MutableList<Station>> = favoriteStations

    /**
     * favori alanına göre
     * liste filtrelenir ve ui
     * bastılırırl
     */
    fun getFavoriteStations() {
        viewModelScope.launch {
            favoriteStations.value = repository.getStationsFromLocal().filter {
                it.isFavorite
            }.toMutableList()
        }
    }

    /**
     * favoriden kaldırılan urunlerin
     * durumları false cekilir
     * @param station
     */
    fun removeFavoriteItem(station: Station?) {
        viewModelScope.launch {
            station?.isFavorite = false
            station?.let { repository.update(it) }
        }
        getFavoriteStations()
    }
}