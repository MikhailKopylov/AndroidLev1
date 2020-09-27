package com.amk.weatherforall.core.Weather

import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.Weather.weatherFor5Days.WeatherData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("city")
    @Expose
    val city: City,
    @SerializedName("list")
    @Expose
    val list:Array<WeatherData>

)