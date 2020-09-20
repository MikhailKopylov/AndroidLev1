package com.amk.weatherforall.core.Weather.weatherFor5Days

data class Main (
    val temp: Double,
    val tempFeelLike:Double,
    val tempMin:Double,
    val tempMax:Double,
    val pressure:Int,
    val pressureSeaLevel:Int,
    val pressureGrndLevel:Int,
    val humidity:Int,
    val temp_kf:Double
){
}