package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.add_city.ResponseCitiesList
import com.example.weatherapp.repository.AddCityRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCitiesViewModel @Inject constructor(private val addCityRepository: AddCityRepository) : ViewModel() {
    //Cities
    private val _citiesData = MutableLiveData<NetworkRequest<ResponseCitiesList>>()
    val citiesData: LiveData<NetworkRequest<ResponseCitiesList>> = _citiesData

    fun callCitiesApi(q: String) = viewModelScope.launch {
        _citiesData.value = NetworkRequest.Loading()
        val response = addCityRepository.getCitiesList(q)
        _citiesData.value = NetworkResponse(response).generateResponse()
    }
}