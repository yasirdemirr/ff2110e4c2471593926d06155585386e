package com.myd.ff2110e4c2471593926d06155585386e.extensions

fun Int.getCapacityAndDurabilty(): String {
    return this.times(10000).toString()
}

fun Int.getSpeed(): String {
    return this.times(20).toString()
}