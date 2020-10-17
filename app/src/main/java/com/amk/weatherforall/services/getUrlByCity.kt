package com.amk.weatherforall.services

import android.content.Context
import com.amk.weatherforall.R
import com.amk.weatherforall.core.City.City

fun getUrlByCity(city: City, context:Context):String{
    return when(city.name){
        context.resources.getString(R.string.Moscow) -> "https://images.unsplash.com/photo-1594805986553-e110b2d793f3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
        context.resources.getString(R.string.Saratov) -> "https://images.unsplash.com/photo-1600799651038-4c24e1f23bc3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
        context.resources.getString(R.string.Saint_Petersburg) -> "https://images.unsplash.com/photo-1556610961-2fecc5927173?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2120&q=80"
        else -> "https://images.unsplash.com/uploads/1413259835094dcdeb9d3/6e609595?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1352&q=80"
    }
}