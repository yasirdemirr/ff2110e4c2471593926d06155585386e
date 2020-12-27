package com.myd.ff2110e4c2471593926d06155585386e.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "station_information_table")
data class Station(
    @PrimaryKey
    var id: Int? = null,

    @SerializedName("capacity")
    var capacity: Int? = null,

    @SerializedName("coordinateX")
    var coordinateX: Int? = null,

    @SerializedName("coordinateY")
    var coordinateY: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("need")
    var need: Int? = null,

    @SerializedName("stock")
    var stock: Int? = null,

    var isFavorite: Boolean? = false
)