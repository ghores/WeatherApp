package com.example.weatherapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.database.CitiesDao
import com.example.weatherapp.data.database.CitiesDatabase
import com.example.weatherapp.utils.CITIES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CitiesDatabase =
        Room.databaseBuilder(
            context, CitiesDatabase::class.java, CITIES_DATABASE
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: CitiesDatabase): CitiesDao = db.dao()
}