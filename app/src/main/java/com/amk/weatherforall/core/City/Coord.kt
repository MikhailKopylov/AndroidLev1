package com.amk.weatherforall.core.City

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Coord (
    @SerializedName("lat")
    @Expose
    @ColumnInfo(name = "lat")
    val lat: Double,
    @SerializedName("lon")
    @Expose
    @ColumnInfo(name = "lon")
    val lon: Double
){
//    @PrimaryKey(autoGenerate = true) var id:Long = 0
//
//    @ColumnInfo(name = "city_id")
//    var cityId:Long = 0
}