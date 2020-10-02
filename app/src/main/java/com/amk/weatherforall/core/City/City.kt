package com.amk.weatherforall.core.City

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class City(

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "idFromNet")
    val id: Int,

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "cityName")
    val name: String,

    @SerializedName("coord")
    @Expose
    @Embedded
    val coord: Coord,

    @SerializedName("country")
    @Expose
    @ColumnInfo(name = "countryName")
    val country: String,

    @SerializedName("timezone")
    @Expose
    val timezone: String,

    @SerializedName("population")
    @Expose
    @ColumnInfo(name = "population")
    val population: Int,

    @SerializedName("sunrise")
    @Expose
    @ColumnInfo(name = "sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    @Expose
    @ColumnInfo(name = "sunset")
    val sunset: Long

) {
    @PrimaryKey(autoGenerate = true) var idDB:Long = 0

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