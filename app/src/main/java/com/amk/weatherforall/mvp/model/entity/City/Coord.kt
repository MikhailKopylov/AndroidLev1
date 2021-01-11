package com.amk.weatherforall.mvp.model.entity.City

import androidx.room.ColumnInfo
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