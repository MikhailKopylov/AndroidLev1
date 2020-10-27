package com.amk.weatherforall.core.Weather.weatherFor5Days

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main (
    @SerializedName("temp")
    @Expose
    val temp: Double,
    @SerializedName("feels_like")
    @Expose
    val tempFeelLike:Double,
    @SerializedName("temp_min")
    @Expose
    val tempMin:Double,
    @SerializedName("temp_max")
    @Expose
    val tempMax:Double,
    @SerializedName("pressure")
    @Expose
    val pressure:Int,
    @SerializedName("sea_level")
    @Expose
    val pressureSeaLevel:Int,
    @SerializedName("grnd_level")
    @Expose
    val pressureGrndLevel:Int,
    @SerializedName("humidity")
    @Expose
    val humidity:Int,
    @SerializedName("temp_kf")
    @Expose
    val temp_kf:Double
)