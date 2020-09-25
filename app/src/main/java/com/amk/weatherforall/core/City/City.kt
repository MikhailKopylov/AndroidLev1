package com.amk.weatherforall.core.City

data class City (
    val id:Int,
    val name: String ,
    val coord:Coord,
    val country: String,
    val timezone:String
){

    constructor(cityName:String) : this(
        id = 0,
        name = cityName,
        coord = Coord(0.0, 0.0),
        country = "",
        timezone = ""
    ) {
        
    }

    companion object{
        val CITY_DEFAULT = City("Moscow")
    }
}