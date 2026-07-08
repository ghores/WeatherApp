package com.example.weatherapp.repository

import com.example.weatherapp.data.database.CitiesDao
import com.example.weatherapp.data.database.CitiesEntity
import com.example.weatherapp.data.model.main.ResponseCurrentWeather
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.data.network.ApiServices
import com.example.weatherapp.utils.FA
import com.example.weatherapp.utils.METRIC
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val citiesDao: CitiesDao,
    private val apiServices: ApiServices
) {
    //Database
    fun getCities(): Flow<List<CitiesEntity>> = citiesDao.loadCities()

    //Api
    suspend fun getCurrentWeather(lat: Double, lon: Double): Response<ResponseCurrentWeather> = apiServices.getCurrentWeather(lat, lon, FA, METRIC)
    suspend fun getForecast(lat: Double, lon: Double): Response<ResponseForecast> = apiServices.getForecast(lat, lon, FA, METRIC)
}