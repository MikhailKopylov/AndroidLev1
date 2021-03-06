package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.weather.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherCityName {

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("q") city: String,
        @Query("appid") keyApi: String,
        @Query("units") units: String,
        @Query("lang") local:String
    ): Call<WeatherForecast>
}