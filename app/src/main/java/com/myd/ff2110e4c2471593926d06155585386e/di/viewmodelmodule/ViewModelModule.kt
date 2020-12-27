package com.myd.ff2110e4c2471593926d06155585386e.di.viewmodelmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myd.ff2110e4c2471593926d06155585386e.di.key.ViewModelKey
import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageViewModel
import com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle.VehicleBuilderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @get:IntoMap
    @get:Binds
    @get:ViewModelKey(HomePageViewModel::class)
    val HomePageViewModel.homePageViewModel: ViewModel

    @get:IntoMap
    @get:Binds
    @get:ViewModelKey(VehicleBuilderViewModel::class)
    val VehicleBuilderViewModel.vehicleBuilderPageViewModel: ViewModel


    @get:Binds
    val ViewModelFactory.viewModelProviderFactory: ViewModelProvider.Factory
}