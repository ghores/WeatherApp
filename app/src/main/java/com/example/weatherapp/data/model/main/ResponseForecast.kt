package com.example.weatherapp.data.model.main

import com.google.gson.annotations.SerializedName

data class ResponseForecast(
    @SerializedName("city")
    val city: City?,
    @SerializedName("cnt")
    val cnt: Int?, // 40
    @SerializedName("cod")
    val cod: String?, // 200
    @SerializedName("list")
    val list: List<Data>?,
    @SerializedName("message")
    val message: Int? // 0
) {
    data class City(
        @SerializedName("coord")
        val coord: Coord?,
        @SerializedName("country")
        val country: String?, // IT
        @SerializedName("id")
        val id: Int?, // 3163858
        @SerializedName("name")
        val name: String?, // Zocca
        @SerializedName("population")
        val population: Int?, // 4593
        @SerializedName("sunrise")
        val sunrise: Int?, // 1783481995
        @SerializedName("sunset")
        val sunset: Int?, // 1783537311
        @SerializedName("timezone")
        val timezone: Int? // 7200
    ) {
        data class Coord(
            @SerializedName("lat")
            val lat: Double?, // 44.34
            @SerializedName("lon")
            val lon: Double? // 10.99
        )
    }

    data class Data(
        @SerializedName("clouds")
        val clouds: Clouds?,
        @SerializedName("dt")
        val dt: Int?, // 1783555200
        @SerializedName("dt_txt")
        val dtTxt: String?, // 2026-07-09 00:00:00
        @SerializedName("main")
        val main: Main?,
        @SerializedName("pop")
        val pop: Double?, // 0.46
        @SerializedName("rain")
        val rain: Rain?,
        @SerializedName("sys")
        val sys: Sys?,
        @SerializedName("visibility")
        val visibility: Int?, // 10000
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind")
        val wind: Wind?
    ) {
        data class Clouds(
            @SerializedName("all")
            val all: Int? // 4
        )

        data class Main(
            @SerializedName("dew_point")
            val dewPoint: Double?, // 16.33
            @SerializedName("feels_like")
            val feelsLike: Double?, // 21.03
            @SerializedName("grnd_level")
            val grndLevel: Int?, // 946
            @SerializedName("humidity")
            val humidity: Int?, // 75
            @SerializedName("pressure")
            val pressure: Int?, // 1011
            @SerializedName("sea_level")
            val seaLevel: Int?, // 1011
            @SerializedName("temp")
            val temp: Double?, // 20.92
            @SerializedName("temp_kf")
            val tempKf: Double?, // 0.03
            @SerializedName("temp_max")
            val tempMax: Double?, // 20.92
            @SerializedName("temp_min")
            val tempMin: Double? // 20.89
        )

        data class Rain(
            @SerializedName("3h")
            val h: Double? // 0.69
        )

        data class Sys(
            @SerializedName("pod")
            val pod: String? // n
        )

        data class Weather(
            @SerializedName("description")
            val description: String?, // آسمان صاف
            @SerializedName("icon")
            val icon: String?, // 01n
            @SerializedName("id")
            val id: Int?, // 800
            @SerializedName("main")
            val main: String? // Clear
        )

        data class Wind(
            @SerializedName("deg")
            val deg: Int?, // 238
            @SerializedName("gust")
            val gust: Double?, // 0.78
            @SerializedName("speed")
            val speed: Double? // 1.6
        )
    }
}