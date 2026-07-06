package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.add_city.ResponseCitiesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("geo/1.0/direct")
    suspend fun getCitiesList(@Query("q") q: String, @Query("limit") limit: Int): Response<ResponseCitiesList>
}