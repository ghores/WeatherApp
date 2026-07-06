package com.example.weatherapp.repository

import com.example.weatherapp.data.model.add_city.ResponseCitiesList
import com.example.weatherapp.data.network.ApiServices
import com.example.weatherapp.utils.CITIES_LIMIT
import retrofit2.Response
import javax.inject.Inject

class AddCityRepository @Inject constructor(private val apiServices: ApiServices) {
    //Api
    suspend fun getCitiesList(q: String): Response<ResponseCitiesList> = apiServices.getCitiesList(q, CITIES_LIMIT)
}