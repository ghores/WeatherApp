package com.example.weatherapp.utils.di

import com.example.weatherapp.data.database.CitiesEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn((FragmentComponent::class))
object FragmentModule {
    @Provides
    fun cityEntity() = CitiesEntity()
}