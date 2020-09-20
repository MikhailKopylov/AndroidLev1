package com.amk.weatherforall.core.City

data class City (
    val id:Int,
    val name: String ,
    val coord:Coord,
    val country: String,
    val timezone:String
){
}