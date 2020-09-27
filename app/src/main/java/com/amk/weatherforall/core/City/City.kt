package com.amk.weatherforall.core.City

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("coord")
    @Expose
    val coord: Coord,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("timezone")
    @Expose
    val timezone: String,
    @SerializedName("population")
    @Expose
    val population: Int,
    @SerializedName("sunrise")
    @Expose
    val sunrise: Long,
    @SerializedName("sunset")
    @Expose
    val sunset: Long

) {

    constructor(cityName: String) : this(
        id = 0,
        name = cityName,
        coord = Coord(0.0, 0.0),
        country = "",
        timezone = "",
        population = 0,
        sunrise = 0,
        sunset = 0
    ) {

    }

    companion object {
        val CITY_DEFAULT = City("Moscow")
    }
}