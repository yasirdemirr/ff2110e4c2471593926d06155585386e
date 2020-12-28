package com.myd.ff2110e4c2471593926d06155585386e.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "station_information_table")
data class Station(
    @PrimaryKey
    var id: Int? = null,

    @SerializedName("capacity")
    var capacity: Int = 0,

    @SerializedName("coordinateX")
    var coordinateX: Double = 0.0,

    @SerializedName("coordinateY")
    var coordinateY: Double = 0.0,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("need")
    var need: Int = 0,

    @SerializedName("stock")
    var stock: Int = 0,

    var isFavorite: Boolean = false,

    var distanceTimeCurrentLocation: Int = 0,

    var isCurrentLocation: Boolean = false,

    var capacityFull: Boolean = false
)