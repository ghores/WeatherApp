package com.example.weatherapp.repository

import com.example.weatherapp.data.model.info.ResponsePollution
import com.example.weatherapp.data.network.ApiServices
import retrofit2.Response
import javax.inject.Inject

class InfoRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getPollution(lat: Double, lon: Double): Response<ResponsePollution> = apiServices.getPollution(lat, lon)
}