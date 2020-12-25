package com.myd.ff2110e4c2471593926d06155585386e.resources

sealed class NetworkState<out T> {

    data class Success<T>(val data: T) : NetworkState<T>()

    object Loading : NetworkState<Nothing>()

    data class Error(val exception: Throwable) : NetworkState<Nothing>()
}