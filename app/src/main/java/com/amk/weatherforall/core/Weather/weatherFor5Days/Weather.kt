package com.amk.weatherforall.core.Weather.weatherFor5Days

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    @Expose
    val id:Long,
    @SerializedName("main")
    @Expose
    val main:String,
    @SerializedName("description")
    @Expose
    val description:String,
    @SerializedName("icon")
    @Expose
    val icon:String
) {
}