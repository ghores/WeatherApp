package com.example.weatherapp.data.model.info

import com.google.gson.annotations.SerializedName

data class ResponsePollution(
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("list")
    val list: List<Data>?
) {
    data class Coord(
        @SerializedName("lat")
        val lat: Double?, // 44.34
        @SerializedName("lon")
        val lon: Double? // 10.99
    )

    data class Data(
        @SerializedName("components")
        val components: Components,
        @SerializedName("dt")
        val dt: Int, // 1783595193
        @SerializedName("main")
        val main: Main
    ) {
        data class Components(
            @SerializedName("co")
            val co: Double?, // 119.35
            @SerializedName("nh3")
            val nh3: Double?, // 2.16
            @SerializedName("no")
            val no: Double?, // 0.1
            @SerializedName("no2")
            val no2: Double?, // 0.69
            @SerializedName("o3")
            val o3: Double?, // 118.21
            @SerializedName("pm10")
            val pm10: Double?, // 25.53
            @SerializedName("pm2_5")
            val pm25: Double?, // 16.02
            @SerializedName("so2")
            val so2: Double? // 1.27
        )

        data class Main(
            @SerializedName("aqi")
            val aqi: Int? // 3
        )
    }
}