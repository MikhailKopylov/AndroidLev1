package com.amk.weatherforall.core.interfaces

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.amk.weatherforall.core.City.City

@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Update
    fun updateCity(city:City)

    @Delete
    fun deleteCity(city: City)

    @Query("SELECT *  FROM city")
    fun getAllCities():List<City>

//    @Query("SELECT * FROM city WHERE id = :id")
//    fun getCityById(id:Long)

    @Query("SELECT COUNT() FROM city")
    fun getCountCities():Long


}