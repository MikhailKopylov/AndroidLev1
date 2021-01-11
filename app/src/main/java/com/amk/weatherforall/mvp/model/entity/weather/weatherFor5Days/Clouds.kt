package com.amk.weatherforall.mvp.model.entity.weather.weatherFor5Days

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    @Expose
    val all:Int
)