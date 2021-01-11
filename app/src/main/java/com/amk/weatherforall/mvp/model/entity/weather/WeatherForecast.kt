package com.amk.weatherforall.mvp.model.entity.weather

import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.weather.weatherFor5Days.WeatherData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("city")
    @Expose
    val city: City,
    @SerializedName("list")
    @Expose
    val list: Array<WeatherData>

)