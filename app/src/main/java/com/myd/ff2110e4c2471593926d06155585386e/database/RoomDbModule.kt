package com.myd.ff2110e4c2471593926d06155585386e.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule {
    @Provides
    @Singleton
    fun provideRoomDb(context: Context) = Room
        .databaseBuilder(
            context.applicationContext,
            SpaceDeliveryDataBase::class.java,
            "space_delivery_database"
        )
        .fallbackToDestructiveMigration()
        .build()
}