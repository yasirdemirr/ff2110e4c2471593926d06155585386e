package com.myd.ff2110e4c2471593926d06155585386e

import android.app.Application
import com.myd.ff2110e4c2471593926d06155585386e.di.DaggerAppBuilderComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector
    override fun onCreate() {
        super.onCreate()
        DaggerAppBuilderComponent
            .factory()
            .create(this)
            .inject(this)
    }
}