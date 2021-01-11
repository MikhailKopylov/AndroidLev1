package com.amk.weatherforall.mvp.model.api

import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherCityNameRx {

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("q") city: String,
        @Query("appid") keyApi: String,
        @Query("units") units: String,
        @Query("lang") local:String
    ): Observable<WeatherForecast>
}