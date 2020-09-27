package com.amk.weatherforall.core.interfaces

import com.amk.weatherforall.core.Weather.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeather {

    @GET("data/2.5/forecast")
    fun loadWeather(@Query("q") city:String, @Query("appid") keyApi:String,
    @Query("units") units:String): Call<WeatherForecast>
}