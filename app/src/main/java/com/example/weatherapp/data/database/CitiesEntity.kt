package com.example.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.utils.CITIES_TABLE

@Entity(tableName = CITIES_TABLE)
data class CitiesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String? = null,
    var lat: Double? = null,
    var lon: Double? = null
)
