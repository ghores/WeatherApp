package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CitiesEntity
import com.example.weatherapp.repository.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val citiesRepository: CitiesRepository) : ViewModel() {
    //Load all cities
    private val _citiesData = MutableLiveData<List<CitiesEntity>>()
    val citiesData: LiveData<List<CitiesEntity>> = _citiesData

    fun callCitiesData() = viewModelScope.launch {
        citiesRepository.getCities().collect {
            _citiesData.value = it
        }
    }

    //Delete
    fun deleteCity(entity: CitiesEntity) = viewModelScope.launch {
        citiesRepository.deleteCity(entity)
    }
}