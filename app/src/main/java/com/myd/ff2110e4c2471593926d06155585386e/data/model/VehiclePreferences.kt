package com.myd.ff2110e4c2471593926d06155585386e.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehiclePreferences(
    var name: String? = null,
    var durability: Int = 0,
    var speed: Int = 0,
    var capacity: Int = 0,
    var currentLocationX: Double = 0.0,
    var currentLocationY: Double = 0.0,
    var currentLocationName: String? = null
) : Parcelable

fun VehiclePreferences.setValues() {
    this.durability = durability.times(10000)
    this.speed = speed.times(20)
    this.capacity = capacity.times(10000)
}