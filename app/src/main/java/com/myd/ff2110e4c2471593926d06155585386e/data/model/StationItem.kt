package com.myd.ff2110e4c2471593926d06155585386e.data.model

import com.google.gson.annotations.SerializedName

data class StationItem(
    @SerializedName("capacity")
    val capacity: Int? = null,
    @SerializedName("coordinateX")
    val coordinateX: Int? = null,
    @SerializedName("coordinateY")
    val coordinateY: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("need")
    val need: Int? = null,
    @SerializedName("stock")
    val stock: Int? = null
)