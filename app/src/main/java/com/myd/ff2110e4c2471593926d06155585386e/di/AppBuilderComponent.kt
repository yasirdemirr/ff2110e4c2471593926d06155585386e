package com.myd.ff2110e4c2471593926d06155585386e.di

import android.content.Context
import com.myd.ff2110e4c2471593926d06155585386e.App
import com.myd.ff2110e4c2471593926d06155585386e.database.RoomDbModule
import com.myd.ff2110e4c2471593926d06155585386e.di.viewmodelmodule.ViewModelModule
import com.myd.ff2110e4c2471593926d06155585386e.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        FragmentBuilderModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        RoomDbModule::class
    ]
)
interface AppBuilderComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AndroidInjector<App>
    }
}