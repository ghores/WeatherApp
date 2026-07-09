package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.info.ResponsePollution
import com.example.weatherapp.repository.InfoRepository
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val infoRepository: InfoRepository) : ViewModel() {
    //Pollution
    private val _pollutionData = MutableLiveData<NetworkRequest<ResponsePollution>>()
    val pollutionData: LiveData<NetworkRequest<ResponsePollution>> = _pollutionData

    fun callPollutionApi(lat: Double, lon: Double) = viewModelScope.launch {
        _pollutionData.value = NetworkRequest.Loading()
        val response = infoRepository.getPollution(lat, lon)
        _pollutionData.value = NetworkResponse(response).generateResponse()
    }
}