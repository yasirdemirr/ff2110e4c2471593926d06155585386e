package com.myd.ff2110e4c2471593926d06155585386e.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import kotlin.math.pow
import kotlin.math.sqrt

@BindingAdapter("distanceText")
fun TextView?.distanceText(distance: Int) {
    this?.text = if (distance != 0) "$distance EUS" else null
}

fun VehiclePreferences.getDistance(x2: Double, y2: Double): Int {
    return sqrt(
        (this.currentLocationX.minus(x2).pow(2)) +
                (this.currentLocationY.minus(y2).pow(2))
    ).toInt()
}

@BindingAdapter("checkFavorite")
fun ImageView?.checkFavorite(isFavorite: Boolean) {
    this?.setImageResource(if (isFavorite) R.drawable.ic_baseline_star_rate_24 else R.drawable.ic_baseline_star_24)
}

@BindingAdapter("checkCapacityVisible")
fun TextView?.checkCapacityVisible(capacity: Int) {
    this?.visibility = if (capacity != 0) View.VISIBLE else View.GONE
}

@BindingAdapter("capacityText")
fun TextView?.capacityText(capacity: Int) {
    this?.text = if (capacity != 0) "$capacity" else null
}