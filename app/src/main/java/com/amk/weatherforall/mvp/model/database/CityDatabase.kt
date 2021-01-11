package com.amk.weatherforall.mvp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amk.weatherforall.mvp.model.entity.City.City
import com.amk.weatherforall.mvp.model.entity.City.DateLastUseCity
import com.amk.weatherforall.mvp.interfaces.CityDAO

@Database(entities = [City::class, DateLastUseCity::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDAO():CityDAO
}