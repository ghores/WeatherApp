package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Insert
    suspend fun saveCity(entity: CitiesEntity)

    @Delete
    suspend fun deleteCity(entity: CitiesEntity)

    @Query("SELECT * FROM CITIES_TABLE")
    fun loadCities(): Flow<List<CitiesEntity>>
}