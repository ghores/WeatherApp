package com.example.weatherapp.data.model.main


import com.google.gson.annotations.SerializedName

data class ResponseCurrentWeather(
    @SerializedName("base")
    val base: String?, // stations
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("cod")
    val cod: Int?, // 200
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("dt")
    val dt: Int?, // 1783530613
    @SerializedName("id")
    val id: Int?, // 3163858
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val name: String?, // Zocca
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?, // 7200
    @SerializedName("visibility")
    val visibility: Int?, // 10000
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("wind")
    val wind: Wind?
) {
    data class Clouds(
        @SerializedName("all")
        val all: Int? // 42
    )

    data class Coord(
        @SerializedName("lat")
        val lat: Double?, // 44.34
        @SerializedName("lon")
        val lon: Double? // 10.99
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double?, // 305.39
        @SerializedName("grnd_level")
        val grndLevel: Int?, // 944
        @SerializedName("humidity")
        val humidity: Int?, // 55
        @SerializedName("pressure")
        val pressure: Int?, // 1009
        @SerializedName("sea_level")
        val seaLevel: Int?, // 1009
        @SerializedName("temp")
        val temp: Double?, // 303.36
        @SerializedName("temp_max")
        val tempMax: Double?, // 304.94
        @SerializedName("temp_min")
        val tempMin: Double? // 303.19
    )

    data class Sys(
        @SerializedName("country")
        val country: String?, // IT
        @SerializedName("id")
        val id: Int?, // 2004688
        @SerializedName("sunrise")
        val sunrise: Int?, // 1783481995
        @SerializedName("sunset")
        val sunset: Int?, // 1783537311
        @SerializedName("type")
        val type: Int? // 2
    )

    data class Weather(
        @SerializedName("description")
        val description: String?, // ابرهای پراکنده
        @SerializedName("icon")
        val icon: String?, // 03d
        @SerializedName("id")
        val id: Int?, // 802
        @SerializedName("main")
        val main: String? // Clouds
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Int?, // 218
        @SerializedName("gust")
        val gust: Double?, // 3.95
        @SerializedName("speed")
        val speed: Double? // 1.9
    )
}