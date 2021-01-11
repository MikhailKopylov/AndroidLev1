package com.amk.weatherforall.services

import com.amk.weatherforall.R

fun drawable(icon:String):Int{
   return when(icon){
        "01d" -> R.drawable.d01
        "01n" -> R.drawable.n01
        "02d" -> R.drawable.d02
        "02n" -> R.drawable.n02
        "03d" -> R.drawable.d03
        "03n" -> R.drawable.n03
        "04d" -> R.drawable.d04
        "04n" -> R.drawable.n04
        "09d" -> R.drawable.d09
        "09n" -> R.drawable.n09
        "10d" -> R.drawable.d10
        "10n" -> R.drawable.n10
        "11d" -> R.drawable.d11
        "11n" -> R.drawable.n11
        "13d" -> R.drawable.d13
        "13n" -> R.drawable.n13
        "50d" -> R.drawable.d50
        "50n" -> R.drawable.n50
        else -> R.drawable.nothing
    }
}