package com.myd.ff2110e4c2471593926d06155585386e.di

import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageFragment
import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageModule
import com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle.VehicleBuilderFragment
import com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle.VehicleBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {
    @get:ContributesAndroidInjector(modules = [HomePageModule::class])
    val homePageFragment: HomePageFragment

    @get:ContributesAndroidInjector(modules = [VehicleBuilderModule::class])
    val vehicleFragment: VehicleBuilderFragment
}