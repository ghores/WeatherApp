package com.example.weatherapp.repository

import com.example.weatherapp.data.database.CitiesDao
import com.example.weatherapp.data.database.CitiesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val citiesDao: CitiesDao) {
    //Database
    fun getCities(): Flow<List<CitiesEntity>> = citiesDao.loadCities()
}