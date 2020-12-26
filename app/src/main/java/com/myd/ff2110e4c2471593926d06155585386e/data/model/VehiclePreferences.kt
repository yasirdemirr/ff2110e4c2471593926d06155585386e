package com.myd.ff2110e4c2471593926d06155585386e.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehiclePreferences(
    var name: String? = null,
    var durability: Int = 0,
    var speed: Int = 0,
    var capacity: Int = 0
) : Parcelable