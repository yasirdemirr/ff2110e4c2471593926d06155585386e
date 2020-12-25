package com.myd.ff2110e4c2471593926d06155585386e.di

import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageFragment
import com.myd.ff2110e4c2471593926d06155585386e.ui.home.HomePageModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {
    @get:ContributesAndroidInjector(modules = [HomePageModule::class])
    val homePageFragment: HomePageFragment
}