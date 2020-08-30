package com.amk.weatherforall.core.interfaces

interface Publisher {

    fun subscribe(observable: Observable)
    fun unsubscribe(observable: Observable)
    fun notify(text:String)
}