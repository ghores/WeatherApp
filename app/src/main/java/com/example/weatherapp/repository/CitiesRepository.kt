package com.example.weatherapp.repository

import com.example.weatherapp.data.database.CitiesDao
import com.example.weatherapp.data.database.CitiesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val dao: CitiesDao) {
    fun getCities(): Flow<List<CitiesEntity>> = dao.loadCities()
    suspend fun deleteCity(entity: CitiesEntity) = dao.deleteCity(entity)
}