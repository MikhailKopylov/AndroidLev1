package com.amk.weatherforall.core.Weather.weatherFor5Days

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed:Double,
    @SerializedName("deg")
    @Expose
    val deg:Int
)