package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CitiesEntity
import com.example.weatherapp.data.model.main.ResponseCurrentWeather
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    //Cities from database
    private val _citiesData = MutableLiveData<List<CitiesEntity>>()
    val citiesData: LiveData<List<CitiesEntity>> = _citiesData

    fun callCitiesData() = viewModelScope.launch {
        mainRepository.getCities().collect {
            _citiesData.value = it
        }
    }

    //Api
    //Current weather
    private val _currentWeatherData = MutableLiveData<NetworkRequest<ResponseCurrentWeather>>()
    val currentWeatherData: LiveData<NetworkRequest<ResponseCurrentWeather>> = _currentWeatherData

    fun callCurrentWeatherApi(lat: Double, lon: Double) = viewModelScope.launch {
        _currentWeatherData.value = NetworkRequest.Loading()
        val response = mainRepository.getCurrentWeather(lat, lon)
        _currentWeatherData.value = NetworkResponse(response).generateResponse()
    }

    //Forecast
    private val _forecastWeatherData = MutableLiveData<NetworkRequest<ResponseForecast>>()
    val forecastWeatherData: LiveData<NetworkRequest<ResponseForecast>> = _forecastWeatherData

    fun callForecastWeatherApi(lat: Double, lon: Double) = viewModelScope.launch {
        _forecastWeatherData.value = NetworkRequest.Loading()
        val response = mainRepository.getForecast(lat, lon)
        _forecastWeatherData.value = NetworkResponse(response).generateResponse()
    }
}