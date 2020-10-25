package com.amk.weatherforall.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amk.weatherforall.core.City.City
import com.amk.weatherforall.core.City.DateLastUseCity
import com.amk.weatherforall.core.interfaces.CityDAO

@Database(entities = [City::class, DateLastUseCity::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDAO():CityDAO
}