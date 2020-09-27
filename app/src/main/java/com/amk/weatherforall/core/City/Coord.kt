package com.amk.weatherforall.core.City

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord (
    @SerializedName("lon")
    @Expose
    val lon: Double ,
    @SerializedName("lat")
    @Expose
    val lat: Double
){


}