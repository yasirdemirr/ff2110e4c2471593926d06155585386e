package com.myd.ff2110e4c2471593926d06155585386e.di.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * created by yasirDemir
 */
@Singleton
class ViewModelFactory @Inject constructor(private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var viewModel = viewModelMap[modelClass]

        if (viewModel == null) {
            for (entry in viewModelMap) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    viewModel = entry.value
                    break
                }
            }
        }
        if (viewModel == null) throw IllegalArgumentException("Unknow Type:$modelClass")
        return viewModel.get() as T
    }
}