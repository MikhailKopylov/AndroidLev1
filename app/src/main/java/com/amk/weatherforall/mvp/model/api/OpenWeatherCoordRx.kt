package com.amk.weatherforall.mvp.model.api

import com.amk.weatherforall.mvp.model.entity.weather.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherCoordRx {

    @GET("data/2.5/forecast")
    fun loadWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") keyApi: String,
        @Query("units") units: String,
        @Query("lang") local: String
    ): Observable<WeatherForecast>
}