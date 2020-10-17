package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherCoord {

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") keyApi: String,
        @Query("units") units: String,
        @Query("lang") local:String
    ): Call<WeatherForecast>
}