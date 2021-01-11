package com.amk.weatherforall.mvp.model.entity.weather.weatherFor5Days

import com.amk.weatherforall.services.DateTimeUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherData(
//    val temp: Temperature,
//    val pressure:Double,
//    val humidity:Int,
//    val weather: Array<Weather>,
//    val speed:Double,
//    val deg: Int,
//    val clouds: Int
    @SerializedName("dt")
    @Expose
    private val dateTime:Long,
    @SerializedName("main")
    @Expose
    val main:Main,
    @SerializedName("weather")
    @Expose
    val weather:Array<Weather>,
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,
    @SerializedName("wind")
    @Expose
    val wind:Wind,
    @SerializedName("visibility")
    @Expose
    val visibility:Int,
    @SerializedName("pop")
    @Expose
    val pop:Double,
    @SerializedName("sys")
    @Expose
    val sys:Sys,
    @SerializedName("dt_txt")
    @Expose
    val dt_txt:String
){
    fun getDateTime():Long{
        val offSet:Int = TimeZone.getDefault().getOffset(System.currentTimeMillis())
        return    dateTime + offSet / DateTimeUtils.ADD_TO_MILLISECONDS
    }
}
